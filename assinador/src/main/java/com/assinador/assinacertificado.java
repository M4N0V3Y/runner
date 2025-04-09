package com.assinador;

import com.RunnerUtils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.SwingUtilities;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSTypedData;

//import javafx.scene.paint.Color;

/**
 * JavaFX assinacertificado
 */
public class assinacertificado extends JFrame implements ActionListener, BackEndObserver {
	private static assinacertificado frame;
	private static JLabel label;
	private static JComboBox<String> comboBox;
	private static JProgressBar pbar;
	private static JButton closeButton;
	private static JButton signButton;
	private static JTextArea textArea;
	static JScrollPane sp;
	private static String[] items; // = { "Item 1", "Item 2", "Item 3" };
	// private static Certificate[] certificates;
	@SuppressWarnings("unused")
	private static String fullFilePath;
	private static String fullFilePath2;
	private static String fullFilePathDoc;
	private static String fullFilePathSecond;
	private static LoadCertificates loadCertificates;
	// private static SignatureWrapper signatureWrapper;
	private static SignDocumentFromWeb signDocumentFromWeb;
	private static VersionInfo versionInfo;

	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;
	static int countNaoAssinado;

	private static PdfSigner pdfSigner;
	private static PdfSignatureHelper pdfSignatureHelper;
	private static PdfWs ws;
	private static String _version;
	private static String idsToSign;
	private static String servidor;
	private static String chave;
	private static String data_hora;
	private static String normal_zipado;
	private static String versao_assinador;
	private static String api_assinador;
	private static String parametro_atualizcao;
	private static String cpf_cnpj;
	private static String chave_documento;
	private static Logger logger;
	private static String _GETDOC_SIGN_PSW = "@$$in@.n&t";
	private static String _app_version = "9.0.18";
	private static int contaAssinaturas;
	private static int count = 1;
	private static int iErroCritico = 0;

	/**
	 * 
	 */
	private static void cleanUp() {
		// Nullify references and clear data
		// ...

		// Suggest garbage collection
		System.gc();
	}

	public assinacertificado(String title) {
		super(title);

	}

	/*
	 * 
	 */
	private static void comboBoxAction() {
		String selectedItem = (String) comboBox.getSelectedItem();
		localUpdate("Certificado selecionado: []" + selectedItem + " ]");

	}

	/*
	 * 
	 */
	private static void closeButtonAction() {
		logger.close();
		frame.dispose();
		cleanUp();
		System.exit(0);
	}

	/*
	 * 
	 */
	private static void signButtonAction() {
		String selectedItem = (String) comboBox.getSelectedItem();
		// String originalStr = textArea.getText();
		// sign all documents from Web Service
		SignAllDocuments(idsToSign, servidor, chave, selectedItem);

	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboBox) {
			comboBoxAction();
		}
		if (e.getSource() == closeButton) {
			closeButtonAction();
		}

		if (e.getSource() == signButton) {
			signButtonAction();
		}
	}

	/**
	 * assinacertificado entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Set the default encoding to UTF-8 - Force pt_BR to show correct Brazilian
		// Portuguese charset
		// System.setProperty("file.encoding", "UTF-8");
		// Attempt to set file.encoding and other related properties
		System.setProperty("file.encoding", "UTF-8");
		System.setProperty("sun.jnu.encoding", "UTF-8"); // May or may not have effect
		System.setProperty("stdout.encoding", "UTF-8"); // May or may not have effect
		System.setProperty("stderr.encoding", "UTF-8"); // May or may not have effect

		///
		versionInfo = new VersionInfo();
		countNaoAssinado = 0;
		// *
		// USE ALWAYS YOU NEED TO DEBUG
		// START - Only for debug - remove it
		// args = new String[2];
		// PDF -
		// args[0] =
		// "assinacertificado:31005232#http://wcfassinanetsuporte.assina.net.br/arquivo.svc#2160#09/04/2025_12:26:08#AN#3#http://wcfapisuporte.assina.net.br#S;15/08/2020##";
		// P7S args[0] =
		// "assinacertificado:32661916#http://wcfassinanetsuporte.assina.net.br/arquivo.svc#2160#26/03/2025_17:29:05#AN#3#http://wcfapisuporte.assina.net.br#S;15/08/2020##";

		// END - Only for debug - remove it
		// */
		logger = Logger.getInstance();

		_version = VersionInfo.getVersion();

		try {
			// Não pode ser usado antes de inicializar os componentes do Frame
			// localUpdate("Argumentos");
			String sEntryCommand = args[0];
			logger.log("[ INFO ✔  ] Argumentos na linha de entrada : " + sEntryCommand);
			String[] arguments = sEntryCommand.split("#");

			for (String arg : arguments) {
				// localUpdate("" + arg);
				logger.log(" " + arg);
			}
			try {
				logger.log("Argumentos tratados: ");
				idsToSign = arguments[0];
				String[] id_part = idsToSign.split(":");
				idsToSign = id_part[1];

				logger.log("Iid(s) do(s) Arquivo: " + idsToSign);
				servidor = arguments[1];
				logger.log("Servidor:             " + servidor);

				data_hora = arguments[3]; // 30/11/2024_16:53:16#
				logger.log("Data e hora do Servidor: " + data_hora);
				normal_zipado = arguments[4]; // # (AN) normal ou (AZ) zipado.
				versao_assinador = arguments[5]; //// 6º parâmetro # versão para o assinador. (0) VERSÃO 1 # (2) VERSÃO
													//// 2 #
													//// (3) VERSÃO 2020
				chave = arguments[2]; // + "#" + versao_assinador;
				logger.log("Chave:               " + chave);
				api_assinador = arguments[6]; // API do assinador
				parametro_atualizcao = arguments[7]; // Parâmetro de atualização
				cpf_cnpj = arguments[8];// CPF ou CPNJ de quem está assinando
				chave_documento = arguments[9];// Chave para pegar ou devolver um documento

			} catch (Exception e) {

				logger.log("Argumentos não assinalados");

			}

			logger.log("Id do arquivo" + idsToSign + "Servidor: " + servidor + "Chave: " + chave);
			InitComponents();
			comboBox.addActionListener(frame);
			closeButton.addActionListener(frame);
			signButton.addActionListener(frame);
			localUpdate("Assinador está em execução");

		} catch (Exception e) {
			logger.log("correu uma exceção na execuçãodo Assinador");
			logger.log(e.getMessage());

			for (StackTraceElement trace : e.getStackTrace()) {
				logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  (" + trace.getLineNumber()
						+ ") ]  -  " + trace.toString());

			}

			// e.printStackTrace();
		}

	}

	/**
	* 
	*/
	private static void InitComponents() {
		try {

			logger.log("Iniciando a construção da interface do  Assinador");
			frame = new assinacertificado("Assinador");
			Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(640, 480);
			frame.setLayout(null);

			logger.log("Carregando os certificados...");
			loadCertificates = new LoadCertificates(frame);
			// LoadCertificates.load();
			items = LoadCertificates.getCertificateNames().toArray(new String[0]);
			comboBox = new JComboBox<>(items);
			closeButton = new JButton("Retornar");
			signButton = new JButton("Assinar");

			label = new JLabel("lblAssinador");
			label.setText("Assinador");
			textArea = new JTextArea();
			textArea.setText("log stack");

			label.setBounds(20, 45, 100, 30);
			frame.add(label);

			comboBox.setBounds(20, 85, 350, 30);
			frame.add(comboBox);

			closeButton.setBounds(500, 390, 100, 30);
			frame.add(closeButton);

			signButton.setBounds(380, 85, 80, 30);
			frame.add(signButton);

			textArea.setBounds(20, 145, 550, 100); // x, y, width, height
			textArea.setRows(15);
			textArea.setColumns(65);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);

			sp = new JScrollPane(textArea);

			sp.setBounds(20, 145, 550, 100);
			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			frame.getContentPane().add(sp);
			// frame.add(statusBar, BorderLayout.SOUTH);

			// initialize Progress Bar
			pbar = new JProgressBar();
			pbar.setMinimum(MY_MINIMUM);
			pbar.setMaximum(MY_MAXIMUM);
			pbar.setBackground(Color.LIGHT_GRAY);
			pbar.setForeground(Color.GREEN);
			pbar.setBounds(20, 390, 480, 30);
			pbar.setBorder(border);
			pbar.setValue(MY_MINIMUM);
			// add to JPanel
			frame.add(pbar);

			frame.setLocationRelativeTo(null); // This line centers the frame
			frame.setVisible(true);
			logger.log("Interface criada e em exibição");
			ws = new PdfWs(frame, servidor, chave);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			logger.log("Dump Exception");
			for (StackTraceElement trace : e.getStackTrace()) {
				logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  (" + trace.getLineNumber()
						+ ") ]  -  " + trace.toString());

			}
		}

	}

	/**
	 * 
	 * @param idsToSign
	 * @param servidor
	 * @param chave
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked", "deprecation" })
	static void SignAllDocuments(String idsToSign, String servidor, String chave, String certAlias) {
		// -----------------------
		// Assinar os documentos.
		// -----------------------
		try {
			closeButton.enable(false);
			String log_msg = "";
			log_msg = "[Info ⚠ ] - Iniciando o componente assinador do(s) documentosr";
			localUpdate(log_msg);

			pdfSigner = new PdfSigner(frame);
			pdfSignatureHelper = new PdfSignatureHelper();

			// String[] id_part = idsToSign.split(":");

			// String sPrefixo = id_part[0];
			// idsToSign = id_part[1];

			log_msg = "[Info ⚠ ] - Iniciando a assinatura do(s) documentosr";
			localUpdate(log_msg);
			log_msg = "[Info ⚠ ] - Id do arquivo" + idsToSign + "Servidor: " + servidor + "Chave: " + chave;
			localUpdate(log_msg);
			log_msg = "[Info ⚠ ] - Certificado em uso: " + certAlias;
			localUpdate(log_msg);

			log_msg = "[Info ⚠ ] - Componente WCF API - Inicializado";
			localUpdate(log_msg);

			List<String> idDocsNotSigned = new ArrayList<String>();
			int size = idsToSign.split(";").length;

			String sRetorno = null;
			// ByteArrayInputStream signedPdf;
			byte[] signedPdf;
			String id = null;
			int iQtdeAssina = 0;

			// pbar.setValue(count);

			// localUpdate("Prefixo: " + sPrefixo);
			localUpdate("Assinate/Documento(s) : [ " + idsToSign + " ]");

			for (String idNovo : idsToSign.split(";")) {
				List<DocumentData> pdfsParaAssinar = new ArrayList<DocumentData>();
				ConcurrentLinkedQueue<DocumentData> pdfsParaAssinarQueue = new ConcurrentLinkedQueue<>();

				try {
					if (idNovo.contains(",")) {
						String[] parts = idNovo.split(",");
						id = parts[0];
						iQtdeAssina = Integer.parseInt(parts[1]);
					} else {
						id = idNovo;
						iQtdeAssina = 1;
					}

					localUpdate("Iniciando a assinatura de cada documento");
					localUpdate("Id do arquivo" + id + "\nServidor: " + servidor + "\nChave: " + chave);

					if (iErroCritico == 0) {
						localUpdate(" [Info ⚠] -  Não occorreu nenum erro crítico.");
						byte[] pdf = { 0x00 };
						try {

							String documentoAssinar = ws.getDocumentoAssinar(_GETDOC_SIGN_PSW, Integer.parseInt(chave),
									id + "#" + _app_version); // + _version);

							if (documentoAssinar.contains("Erro")) {
								localUpdate("[ Erro ] - Documento(s) para assinar: [ " + documentoAssinar
										+ " ] não encontrado(s) no servidor: "
										+ servidor + " com a chave: " + chave);
							}

							localUpdate("Documento(s) para assinar: [ " + documentoAssinar + " ] lido(s) no servidor: "
									+ servidor + " com a chave: " + chave);

							localUpdate("Buscando arquivo no servidor: Id do arquivo " + id + "Servidor: "
									+ servidor + "Chave: " + chave);

							String[] segmentos = documentoAssinar.split("#");
							List<String> idsDocumento = new ArrayList<String>();
							// int contaAssinaturas=0;
							for (String segmento : segmentos) {
								String[] nomeFuncoes = (segmento).split("-");
								String responsavel = nomeFuncoes[0];
								String[] partes = (nomeFuncoes[1]).split(";");
								String[] regrasAssinatura = (partes[0]).split(",");
								localUpdate("Responsável   [  " + responsavel + "  ]");
								contaAssinaturas = 0;
								for (int index = 0; index < regrasAssinatura.length; index++) {
									localUpdate(".... Regar de assinatura:  [  " + regrasAssinatura[index] + "  ]");
									contaAssinaturas++;

								}

								// Um ou mais documentos a assinar
								for (int ndx = 1; ndx < partes.length; ndx++) {
									String numeroDocumento = partes[ndx];
									localUpdate("........ Documento a ser assinado:  " + numeroDocumento);
									idsDocumento.add(numeroDocumento);
								}
							}
							// ler os 5 primeiros;
							// a cada arquivo que for assinado e enviado ler um novo arquivo
							// processamento precisa ser assíncrono
							final int block_of_files = 5;
							ExecutorService executorService = Executors.newFixedThreadPool(block_of_files);
							// Preenche a list de ids de documentos com os 5 primriros Ids
							// Iterator<String> docsTterator = idsDocumento.iterator();
							// Progress bar setup
							int pbar_step = (pbar.getMaximum() + idsDocumento.size()) / idsDocumento.size();
							pbar.setMaximum(idsDocumento.size() * pbar_step);
							// update_status(pbar_step);
							while (!idsDocumento.isEmpty()) {

								// Process one element from the processing queue if it's not empty
								String docID = id;
								// String elementToProcess = idsDocumento.remove(0);

								// submeter cada id tge arquivo para ser assinado
								executorService.submit(() -> {
									try {
										// dynamicaly controls the block_of_files value to ensure
										// that it will deal with the total blocks from isDocument poll and
										// also the reminder
										int remainingDocuments = idsDocumento.size();
										int effectiveBlockSize = Math.min(block_of_files, remainingDocuments);
										// block_of_files = effectiveBlockSize;

										while (pdfsParaAssinarQueue.size() < effectiveBlockSize) {
											String elementToProcess = idsDocumento.remove(0);
											localUpdate("Submitting to process: " + elementToProcess);
											byte[] fetchedPdf = ws.getPdfBytes(elementToProcess, docID);
											localUpdate("Arquivo Id [ " + docID + " ] lido no servidor: " + servidor
													+ " com a chave: " + elementToProcess);
											localUpdate(String.format(" Tamanho do arquivo em bytes: %d",
													fetchedPdf.length));
											localUpdate(String.format(" Tamanho do arquivo em bytes: %d",
													fetchedPdf.length));
											DocumentData data = new DocumentData(fetchedPdf, elementToProcess);
											pdfsParaAssinarQueue.offer(data);
										}
										SwingUtilities.invokeLater(() -> {
											pbar.setValue(pbar.getValue() + ((effectiveBlockSize * pbar_step) / 2));
											closeButton.enable(false);
										});
										// chama assinaEenviaArquivos
										if (!assinaEenviaArquivos(pdfsParaAssinarQueue, certAlias, contaAssinaturas,
												docID, effectiveBlockSize)) {
											iErroCritico++;
											if (!idDocsNotSigned.contains(idNovo))
												idDocsNotSigned.add(idNovo);
											count--;
											SwingUtilities.invokeLater(() -> {
												pbar.setValue(pbar.getValue() + ((effectiveBlockSize * pbar_step) / 2));
											});
										}
										;

										pdfsParaAssinarQueue.clear();
										// ao inves do qtdAssina
										// limpa pdfsParaAssinar
									} catch (Exception e) {

										localUpdate("[EXEÇÃO X ] Exceção ao tentar ler ou processar o Arquivo Id [ "
												+ docID + " ] do servidor: " + servidor + " com a chave: "
												+ Integer.parseInt(chave));
										localUpdate("[EXEÇÃO X ] : " + e.getMessage());
									}

								});

							}
							// Finish all threads
							// executorService.shutdown();

						} catch (NumberFormatException e) {
							localUpdate("[EXCEÇÃO X] Arquivo " + id + " não disponível");

							// logger.log("[EXCEÇÃO X] Arquivo " + id + " não disponível");
							for (StackTraceElement trace : e.getStackTrace()) {
								logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  ("
										+ trace.getLineNumber() + ") ]  -  " + trace.toString());
							}

						} catch (Exception ex) {
							localUpdate("[EXCEÇÃO X] Arquivo " + id + " não disponível");

							// logger.log("[EXCEÇÃO X] Arquivo " + id + " não disponível");
							for (StackTraceElement trace : ex.getStackTrace()) {
								logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  ("
										+ trace.getLineNumber() + ") ]  -  " + trace.toString());
							}

						}

					}
				} catch (Exception e) {
					String exception_message = "";
					if (e.getMessage().toLowerCase().contains("###")) {
						exception_message = "### Documento (" + id + ") " + e.getMessage() + "";
						localUpdate(exception_message);
						// taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
					} else if (e.getMessage().toLowerCase().contains("key type not supported")) {
						exception_message = "[EXCEÇÃO - DNA ] - ### Documento não assinado (" + id
								+ "). Selecione um certificado válido. ###";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("trusted root authority")) {
						// A certificate chain could not be built to a trusted root authority.
						exception_message = "[EXCEÇÃO - DNA ] ### -  Documento não assinado (" + id
								+ "). Selecione um certificado válido. ###";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("the revocation function")) {
						// AE001: Checar Assinatura: 61 # The revocation function was unable to check
						// revocation because the revocation server was offline.
						exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
								+ "). Servidor de revogação offline. ###";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("confiabilidade")) {
						exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
								+ "). Selecione um certificado válido. ###";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("transport-level")) {
						// A transport-level error has occurred when receiving results from the server.
						// (provider: TCP Provider, error: 0 - The semaphore timeout period has
						// expired.)
						exception_message = "[EXCEÇÃO - DNA ET. ] ### Documento não assinado (" + id
								+ "). Erro na transmissão. ### (ICP Provider error)";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("sendsignedpdf")) {
						exception_message = "[EXCEÇÃO - DNA ET. ] ### Documento não assinado (" + id
								+ "). Erro na transmissão. ### (ICP Provider error)";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("getpdfbytes")) {
						exception_message = "[EXCEÇÃO - DNA ET. ] ### Documento não assinado (" + id
								+ "). Erro na transmissão. ### (ICP Provider error)";
						localUpdate(exception_message);
					} else if (e.getMessage().toLowerCase().contains("exception obtaining signature")
							|| e.getMessage().toLowerCase().contains("inteligente foi removido")) {
						// pdfSigner:exception obtaining signature: o cart�o inteligente foi removido,
						// impossibilitando a comunica��o.
						exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
								+ "). O dispositivo de assinatura foi removido. ###";
						localUpdate(exception_message);
					} else {
						exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
								+ "). ). [Avise o suporte].###";
						localUpdate(exception_message);
					}

					// logger.log(exception_message);
					for (StackTraceElement trace : e.getStackTrace()) {
						logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  ("
								+ trace.getLineNumber() + ") ]  -  " + trace.toString());

					}
					iErroCritico++;
					idDocsNotSigned.add(idNovo);
					count--;
				}

				count++;
				// update_status((int) ((count * MY_MAXIMUM) / size));
			}
			//
			if (!idDocsNotSigned.isEmpty()) {
				localUpdate(" [Atenção ⚠ ]  Nem todos os arquivos foram assinados, apenas os arquivos válidos.");
				localUpdate("[Erro X ]  Arquivos não assinados" + idDocsNotSigned.toString());
				localUpdate("------------------------------------------------------------------------------- ");
			} else {

				localUpdate(" [Info ⚠ ] Todos os arquivos válidos foram assinados.");
			}
		} catch (Exception e) {
			//
			localUpdate(
					"[Exceção X ] - Ocorreu uma exceção ao tentar iniciar o componente assinador do(s) documentos");
			// logger.log("[Exceção X ] - Ocorreu uma exceção ao tentar iniciar o componente
			// assinador do(s) documentos");
			for (StackTraceElement trace : e.getStackTrace()) {
				logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  ("
						+ trace.getLineNumber() + ") ]  -  " + trace.toString());
			}
		}
		closeButton.enable(true);

	}

	/**
	 * 
	 * @param pdfsParaAssinar
	 * @param certAlias
	 * @param chave
	 */
	private static boolean assinaEenviaArquivos(ConcurrentLinkedQueue<DocumentData> pdfsParaAssinarQueue,
			String certAlias, int iQtAssina,
			String chave, int block_of_files) {

		int iQtdeAssina = iQtAssina;
		List<DocumentData> pdfsParaAssinar = new ArrayList<DocumentData>();
		DocumentData element;
		int count = 0;

		while ((element = pdfsParaAssinarQueue.poll()) != null && count < block_of_files) {
			pdfsParaAssinar.add(element);
			count++;
		}

		boolean isAssinado = false;
		for (DocumentData data : pdfsParaAssinar) {
			byte[] pdf = data.getPdf();
			byte[] originalPdf = data.getPdf();

			String id = data.getIdDoc();
			byte[] signedPdf = null; // new ByteArrayInputStream(null);
			//
			if (pdf.length <= 200) {
				// pode ser erro ou o documento est� sendo assinado por outra pessoa.
				// throw new RuntimeException("não disponível. Assinar novamente. ###");
				localUpdate("Ocorreu um erro crítico. Documento [" + id
						+ " ] Arquivo pode estar mal formado, tamanho: " + pdf.length
						+ " bytes. Assinar novamente. ###");
				// throw new RuntimeException("#");
				localUpdate("Verificar se há mais arquivos a processar...");
				break;
			} else {
				localUpdate("[ Info ⚠ ] Arquivo presente [ pdf.length > 200 ].");
				try {
					localUpdate("Iniciando a assinatura do documento: [ " + id + " ]");

					Boolean isPDF = PdfP7SChecker.isPDF(pdf);
					Boolean isP7S = PdfP7SChecker.isP7S(pdf);
					;
					int x = 1;
					while (x <= iQtdeAssina) {
						localUpdate("Iniciando a assinatura do documento: [ " + id
								+ " ] - gerando arquivo P7S.");
						// Use PdfP7SChecker to verify if its PDF or P7S
						if (isPDF) {
							// Testa arquivo lido
							localUpdate("PDF lido do servidor referente ao arquivo : [ " + id + " ]");
							//
							signedPdf = pdfSigner.genP7s(pdf, certAlias, originalPdf);
							localUpdate("Foi assinado um documento PDF para o arquivo : [ " + id + " ]");
							pdf = signedPdf;
							isPDF = false;
							isP7S = true;
							//
							isAssinado = (x >= iQtdeAssina);
						} else if (isP7S) {
							CMSSignedData signedData = null;
							try {
								if (signedPdf == null) {
									signedData = new CMSSignedData(originalPdf);
									pdf = originalPdf;
								} else
									signedData = new CMSSignedData(signedPdf);

								// Extract PDF content
								@SuppressWarnings("null")
								CMSTypedData signedContent = signedData.getSignedContent();
								if (signedContent instanceof CMSProcessableByteArray)
									originalPdf = (byte[]) ((CMSProcessableByteArray) signedContent).getContent();
								else
									throw (new Exception("ERROR - Erro tentando realizar segunda assinatura"));
								signedPdf = pdfSigner.genP7s(pdf, certAlias, originalPdf);
								isAssinado = (x >= iQtdeAssina);
								localUpdate("Foi assinado um documento P7S para o arquivo : [ " + id + " ]");
								pdf = signedPdf;

							} catch (Exception e) {
								signedData = null;
								localUpdate("Ocorreu um erro ao tentar assinar um documento P7S para o arquivo : [ "
										+ id + " ] " + e.getMessage());
								localUpdate("O documento não foi assinado.");
								// pdf = signedPdf;
								isAssinado = false;
							}

						} else {
							//
							// Testa arquivo lido
							localUpdate("O arquivo : [ " + id + " ] lido do servidor está em formato inválido");
							try (FileOutputStream fos = new FileOutputStream("C:\\temp\\lido_do_servidor.bad")) {
								int bytesRead = pdf.length;
								fos.write(pdf, 0, bytesRead);

							} catch (Exception ex) {
								localUpdate("ocorreu um erro no teste do arquivo : [ " + id + " ]");
							}
							//
							// erro no formato do arquivo lido
							localUpdate("O formato do documento : [ " + id
									+ " ] não foi reconhecido e o documento não foi assinado.");
							isAssinado = false;
							break;
						}
						//
						x = x + 1;
					}
					// signedPdf = pdf;
					// pbar.setValue(MY_MINIMUM + 60);
					if (isAssinado) {
						//
						localUpdate("Documento assinado " + x + " vezes, Id: (" + id
								+ ") usando o certificado:  [ "
								+ certAlias + " ]");

						localUpdate("Enviando documento de  Id: (" + id + "),  assinado  usando o certificado:  [ "
								+ certAlias + " ], para o servidor.");
						try {
							String sRetorno = ws.sendSignedPdf(id, signedPdf, chave).trim();
							localUpdate("Documento assinado (" + id + ") enviado para o servidor. ");
							localUpdate("Documento assinado - mensagem de retorno:  [ ]" + sRetorno + " ]. ");
						} catch (Exception e) {
							// throw new RuntimeException("#");
							localUpdate("[ EXCEÇÃO X ] - Documento assinado (" + id
									+ ") não foi enviado para o servidor. ");
							for (StackTraceElement trace : e.getStackTrace()) {
								logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  ("
										+ trace.getLineNumber() + ") ]  -  " + trace.toString());

							}
							throw new RuntimeException("sendSignedPdf:" + e.getMessage().toLowerCase());
						}
					} else
						localUpdate("[ERRO X ] Documento Id: (" + id
								+ ") foi lido com erro. ");
				} catch (Exception e) {
					// throw new RuntimeException("#");
					throw new RuntimeException("pdfSigner:" + e.getMessage().toLowerCase());
				}
			}
		}
		return isAssinado;
	}

	/**
	 * frontend level updates
	 * 
	 * @param status
	 */
	private static void localUpdate(String status) {

		logger.log(status);
		SwingUtilities.invokeLater(() -> {
			String originalStr = textArea.getText();
			textArea.setText(originalStr + "" + status + " ] ");
			// statusBar.setText(status);
		});

	}

	/**
	 * backend level updates
	 * 
	 * @param value
	 */
	// @Override
	public static void update_status(int value) {

		try {
			pbar.setValue(value);
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	@Override
	public void update(String status) {
		//
		localUpdate(status);
		// update_status(int value)
		// }
	}

}