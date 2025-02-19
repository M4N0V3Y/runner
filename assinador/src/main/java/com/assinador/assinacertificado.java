package com.assinador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Color;

//import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
//import javax.swing.border.Border;

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

	/*
		*/
	private static void cleanUp() {
		// Nullify references and clear data
		// ...

		// Suggest garbage collection
		System.gc();
	}

	// private static LoadCertificates loadCertificates;
	/*
	 * public static void main(String[] args) {
	 * launch();
	 * }
	 */

	public assinacertificado(String title) {
		super(title);

	}

	/*
	 * 
	 */
	private static void comboBoxAction() {
		/*
		 * String selectedItem = (String) comboBox.getSelectedItem();
		 * String originalStr = textArea.getText();
		 * textArea.setText(originalStr + "" + selectedItem);
		 */
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
		System.setProperty("file.encoding", "UTF-8");
		versionInfo = new VersionInfo();
		countNaoAssinado = 0;
		// *
		// USE ALWAYS YOU NEED TO DEBUG
		// START - Only for debug - remove it
		args = new String[2];
		args[0] = "assinacertificado:30521798#http://wcfassinanetsuporte.assina.net.br/arquivo.svc#2160#19/02/2025_18:12:14#AN#3#http://wcfapisuporte.assina.net.br#S;15/08/2020##";
		// args[0] =
		// "assinacertificado:36414181#http://wcfassinanetsuporte.assina.net.br/arquivo.svc#2160#19/02/2025_16:58:33#AN#3#http://wcfapisuporte.assina.net.br#S;15/08/2020##";
		// END - Only for debug - remove it
		// */
		logger = Logger.getInstance();

		_version = VersionInfo.getVersion();

		try {
			// Não pode ser usado antes de inicializar os componentes do Frame
			// localUpdate("Argumentos");
			String sEntryCommand = args[0];
			logger.log("Argumentos na linha de entrada : " + sEntryCommand);
			String[] arguments = sEntryCommand.split("#");

			for (String arg : arguments) {
				// localUpdate("" + arg);
				logger.log(" " + arg);
			}
			try {
				logger.log("Argumentos tratados: ");
				idsToSign = arguments[0];
				String[] id_part = idsToSign.split(":");

				// String sPrefixo = id_part[0];
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
				// localUpdate("Argumentos assinalados");

			}

			/*
			 * for (String arg : args) {
			 * localUpdate("" + arg);
			 * 
			 * }
			 */

			// if (arguments.length < 10) {
			// System.err.println("Usage: java Main <servidor> <chave>");
			// System.exit(1);
			// }

			logger.log("Id do arquivo" + idsToSign + "Servidor: " + servidor + "Chave: " + chave);

			// localUpdate("Id do arquivo" + idsToSign + "Servidor: " + servidor + "Chave: "
			// + chave);

			InitComponents();
			comboBox.addActionListener(frame);
			closeButton.addActionListener(frame);
			signButton.addActionListener(frame);
			// signatureWrapper = new SignatureWrapper(frame);
			// signDocumentFromWeb = new SignDocumentFromWeb(frame, args[0]);
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
	static void SignAllDocuments(String idsToSign, String servidor, String chave, String certAlias) {
		// -----------------------
		// Assinar os documentos.
		// -----------------------
		try {
			String log_msg = "";
			log_msg = "[Info ⚠ ] - Iniciando o componente assinador do(s) documentosr";
			localUpdate(log_msg);

			pdfSigner = new PdfSigner(frame);

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
			int count = 1;

			String sRetorno = null;
			int iErroCritico = 0;
			ByteArrayInputStream signedPdf;
			String id = null;
			int iQtdeAssina = 0;

			pbar.setMaximum(size);
			// pbar.setValue(count);
			update_status((int) ((count * MY_MAXIMUM) / size));

			// localUpdate("Prefixo: " + sPrefixo);
			localUpdate("Assinate/Documento(s) : [ " + idsToSign + " ]");

			for (String idNovo : idsToSign.split(";")) {
				List<DocumentData> pdfsParaAssinar = new ArrayList<DocumentData>();
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
						byte[] _buff = { 0x00 };
						byte[] pdf = { 0x00 };
						try {

							String documentoAssinar = ws.getDocumentoAssinar(_GETDOC_SIGN_PSW, Integer.parseInt(chave),
									id + "#" + _app_version); // + _version);

							// String documentoAssinar = ws.getDocumentoAssinar(_GETDOC_SIGN_PSW,
							// Integer.parseInt(id),
							// chave);

							// String documentoAssinar = ws.getDocumentoAssinar(_GETDOC_SIGN_PSW, id + "#" +
							// _version,
							// chave);

							if (documentoAssinar.contains("Erro")) {
								localUpdate("[ Erro ] - Documento(s) para assinar: [ " + documentoAssinar
										+ " ] não encontrado(s) no servidor: "
										+ servidor + " com a chave: " + chave);
							}

							localUpdate("Documento(s) para assinar: [ " + documentoAssinar + " ] lido(s) no servidor: "
									+ servidor + " com a chave: " + chave);

							localUpdate("Buscando arquivo no servidor: Id do arquivo " + id + "Servidor: "
									+ servidor + "Chave: " + chave);
							// "SENEGOID - REPRESENTANTE,SENEGOID - AVALISTA;16365379#SENEGOID -
							// AVALISTA,SENEGOID -
							// REPRESENTANTE;16365385;16365391;16365397;16365403;16365409;16365415;16365421"
							// "SENEGOID - AVALISTA,TESTEMUNHA;445340"
							/*
							 * 
							 * SENEGOID - REPRESENTANTE,SENEGOID - AVALISTA;
							 * 16365379#
							 * SENEGOID - AVALISTA,SENEGOID - REPRESENTANTE;
							 * 16365385;16365391;16365397;16365403;16365409;16365415;16365421
							 */
							// split("#")
							// split(";")
							// split(",")

							String[] segmentos = documentoAssinar.split("#");
							List<String> idsDocumento = new ArrayList<String>();
							for (String segmento : segmentos) {
								String[] partes = segmento.split(";");
								String[] nomeRegraResponsavel = (partes[0]).split(",");
								for (int index = 0; index < nomeRegraResponsavel.length; index++) {
									localUpdate("Responsável / regra " + nomeRegraResponsavel[index]);

								}

								for (int index = 1; index < partes.length; index++) {
									localUpdate("Documento a ser assinado: " + partes[index]);
									idsDocumento.add(partes[index]);
								}

							}
							int totalDeDocumentos = idsDocumento.size();
							int contaDocs = 0;
							for (String idDoc : idsDocumento) {
								contaDocs++;
								// pdf = ws.getPdfBytes(idDoc, chave); - a chave, na verdade, não é o parâmetro
								// 3,
								// a chave é o parâmetro 2

								// iDoc - vem como retorno do getDocumentoAssinar
								// o id aqui é a chave, que vem da linha de argumento

								pdf = ws.getPdfBytes(idDoc, id);

								localUpdate("Arquivo Id [ " + id + " ] lido no servidor: "
										+ servidor + " com a chave: " + idDoc);
								localUpdate(String.format(" Tamanho do arquivo em bytes: %d", pdf.length));
								DocumentData data = new DocumentData(pdf, idDoc);
								pdfsParaAssinar.add(data);
								// se pdfsParaAssinar tiver 5 documentos
								// ou se a contagem de documentos for igual ao total lido:
								if (pdfsParaAssinar.size() == 5 || contaDocs == totalDeDocumentos) {
									// chama assinaEenviaArquivos
									assinaEenviaArquivos(pdfsParaAssinar, certAlias, iQtdeAssina, id);
									// limpa pdfsParaAssinar
									pdfsParaAssinar.clear();
									// reomeça a ler os arquivos
								}

							}

						} catch (Exception e) {
							// pdf= { 0x00 };
							localUpdate("[EXCEÇÃO X] Arquivo " + id + " não disponível");

							// logger.log("[EXCEÇÃO X] Arquivo " + id + " não disponível");
							for (StackTraceElement trace : e.getStackTrace()) {
								logger.log(" [ " + trace.getFileName() + "::" + trace.getMethodName() + "  ("
										+ trace.getLineNumber() + ") ]  -  " + trace.toString());

							}

						}

						// if (!sRetorno.equals(".")) {
						// throw new RuntimeException("#");
						// throw new RuntimeException(sRetorno);
						// }

						// localUpdate(taskOutput.getDocument().getLength());
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
				update_status((int) ((count * MY_MAXIMUM) / size));
			}

			localUpdate(" Todos os arquivos válidos foram assinados.");

			if (idDocsNotSigned.size() > 0) {
				localUpdate("Arquivos não assinados" + idDocsNotSigned.toString());
				localUpdate("------------------------------------------------------------------------------- ");

				// 2ª tentativa de assinatura
				for (String idNovo : idDocsNotSigned) {
					try {
						if (idNovo.contains(",")) {
							String[] parts = idNovo.split(",");
							id = parts[0];
							iQtdeAssina = Integer.parseInt(parts[1]);
						} else {
							id = idNovo;
							iQtdeAssina = 1;
						}

						if (iErroCritico == 0) {
							byte[] pdf = ws.getPdfBytes(id, chave);

							if (pdf.length <= 200) {
								// pode ser erro ou o documento est� sendo assinado por outra pessoa.
								throw new RuntimeException("não disponível. Assinar novamente. ###");
							} else {
								try {
									signedPdf = null;

									for (int x = 1; x <= iQtdeAssina; x = x + 1) {
										signedPdf = pdfSigner.genP7s(pdf, certAlias);
									}

									try {
										sRetorno = ws.sendSignedPdf(id, signedPdf, chave).trim();
									} catch (Exception e) {
										throw new RuntimeException("sendSignedPdf:" + e.getMessage().toLowerCase());
									}
								} catch (Exception e) {
									throw new RuntimeException("pdfSigner:" + e.getMessage().toLowerCase());
								}
							}

							if (!sRetorno.equals(".")) {
								throw new RuntimeException(sRetorno);
							}

							localUpdate("Documento assinado (" + id + ")" + "");
						}
					} catch (Exception e) {
						String exception_message = "";
						if (e.getMessage().toLowerCase().contains("###")) {
							exception_message = "[EXCEÇÃO - DNA ] ### Documento (" + id + ") " + e.getMessage() + "";
							localUpdate(exception_message);

						} else if (e.getMessage().toLowerCase().contains("key type not supported")) {
							exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
									+ "). Selecione um certificado válido. ###";
							localUpdate(exception_message);
							iErroCritico++;
						} else if (e.getMessage().toLowerCase().contains("trusted root authority")) {
							// A certificate chain could not be built to a trusted root authority.
							exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
									+ "). Selecione um certificado válido. ###";
							localUpdate(exception_message);

							iErroCritico++;
						} else if (e.getMessage().toLowerCase().contains("the revocation function")) {
							// AE001: Checar Assinatura: 61 # The revocation function was unable to check
							// revocation because the revocation server was offline
							exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
									+ "). Servidor de revogação offline. ###";
							localUpdate(exception_message);

							iErroCritico++;
						} else if (e.getMessage().toLowerCase().contains("confiabilidade")) {
							exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
									+ "). Selecione um certificado válido. ###";
							localUpdate(exception_message);

							iErroCritico++;
						} else if (e.getMessage().toLowerCase().contains("transport-level")) {
							// A transport-level error has occurred when receiving results from the server.
							// (provider: TCP Provider, error: 0 - The semaphore timeout period has
							// expired.)
							exception_message = "[EXCEÇÃO - DNA .ET. ] ### Documento não assinado (" + id
									+ "). Erro na transmissão. ###";
							localUpdate(exception_message);

						} else if (e.getMessage().toLowerCase().contains("sendsignedpdf")) {
							exception_message = "[EXCEÇÃO - DNA . ET. ] ### Documento não assinado (" + id
									+ "). Erro na transmissão. ###";
							localUpdate(exception_message);

						} else if (e.getMessage().toLowerCase().contains("getpdfbytes")) {
							// getPdfBytes:java.lang.RuntimeException: request: java.io.IOException: Server
							// returned HTTP response code: 500 for URL:
							// http://wcfassinanetsuporte.assina.net.br/Arquivo.svc [Avise o suporte]. ###
							// getPdfBytes:java.lang.RuntimeException: request:
							// java.net.MalformedURLException: no protocol
							exception_message = "[EXCEÇÃO - DNA . ET. ] ### Documento não assinado (" + id
									+ "). Erro na transmissão. ###";
							localUpdate(exception_message);
						} else if (e.getMessage().toLowerCase().contains("exception obtaining signature")
								|| e.getMessage().toLowerCase().contains("inteligente foi removido")) {
							// pdfSigner:exception obtaining signature: o cart�o inteligente foi removido,
							// impossibilitando a comunica��o.

							exception_message = "[EXCEÇÃO - DNA ] ### Documento não assinado (" + id
									+ "). O dispositivo de assinatura foi removido. ###";
							localUpdate(exception_message);
							iErroCritico++;
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
						countNaoAssinado++;
					}

					count++;
					// pbar.setValue(count);
					update_status((int) ((count * MY_MAXIMUM) / size));
				}
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

	}

	/**
	 * 
	 * @param pdfsParaAssinar
	 * @param certAlias
	 * @param chave
	 */
	private static void assinaEenviaArquivos(List<DocumentData> pdfsParaAssinar, String certAlias, int iQtdeAssina,
			String chave) {
		for (DocumentData data : pdfsParaAssinar) {
			byte[] pdf = data.getPdf();

			String id = data.getIdDoc();
			ByteArrayInputStream signedPdf = null; // new ByteArrayInputStream(null);
			//
			if (pdf.length <= 200) {
				// pode ser erro ou o documento est� sendo assinado por outra pessoa.
				// throw new RuntimeException("não disponível. Assinar novamente. ###");
				localUpdate("Ocorreu um erro crítico. Documento [" + id
						+ " ] Arquivo pode estar mal formado, tamanho: " + pdf.length
						+ " bytes. Assinar novamente. ###");
				// throw new RuntimeException("#");
				localUpdate("Verificar se há mais arquivos a processar...");
				continue;

			} else {
				localUpdate("[ Info ⚠ ] Arquivo presente [ pdf.length > 200 ].");
				try {
					localUpdate("Iniciando a assinatura do documento: [ " + id + " ]");
					// byte[] fileToSign = pdf;

					// Use PdfP7SChecker to verify if its PDF or P7S
					// Adjustt the P7SSigner class to sign a P7S in case of being a P7S
					// improve this cahnges to make it sign both PDF and P7F on demand
					// byte[] fileToSign = pdf.toString().getBytes();

					int x = 1;
					while (x <= iQtdeAssina) {
						// signedPdf = pdfSigner.genP7s(signedPdf,
						// certificates.getSelectedItem().toString());
						// byte[] fileToSign = byteArrayOutputStream.toByteArray(); //
						// Base64.encodeBase64(byteArrayOutputStream.toByteArray());
						localUpdate("Iniciando a assinatura do documento: [ " + id
								+ " ] - gerando arquivo P7S.");
						Boolean isSigned = false;
						// Use PdfP7SChecker to verify if its PDF or P7S
						if (PdfP7SChecker.isPDF(pdf)) {
							// Testa arquivo lido
							localUpdate("PDF lido do servidor referente ao arquivo : [ " + id + " ]");
							//
							signedPdf = pdfSigner.genP7s(pdf, certAlias);
							signedPdf.mark(Integer.MAX_VALUE);
							localUpdate("Foi assinado um documento PDF para o arquivo : [ " + id + " ]");
							//

							isSigned = true;
						}

						else if (PdfP7SChecker.isP7S(pdf)) {
							signedPdf = pdfSigner.signP7s(pdf, certAlias);
							signedPdf.mark(Integer.MAX_VALUE);
							localUpdate("Foi assinado um documento P7S para o arquivo : [ " + id + " ]");
							isSigned = true;
						}

						else {
							//
							// Testa arquivo lido
							localUpdate("O arquivo : [ " + id + " ] lido do servidor está em formato inválido");
							try (FileOutputStream fos = new FileOutputStream("C:\\temp\\lido_do_servidor.bad")) {
								// byte[] buffer = new byte[64]; // Buffer size
								int bytesRead = pdf.length;
								// while ((bytesRead = pdf.read(buffer)) != -1) {
								fos.write(pdf, 0, bytesRead);
								// }

							} catch (Exception ex) {
								localUpdate("ocorreu um erro no teste do arquivo : [ " + id + " ]");
							}
							//
							// erro no formato do arquivo lido
							localUpdate("O formato do documento : [ " + id
									+ " ] não foi reconhecido e o documento não foi assinado.");

						}
						// pbar.setValue(MY_MINIMUM + 60);
						if (isSigned) {
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
						//
						x = x + 1;

					}
					// signedPdf = pdf;

				} catch (Exception e) {
					// throw new RuntimeException("#");
					throw new RuntimeException("pdfSigner:" + e.getMessage().toLowerCase());
				}
			}

		}
	}

	/**
	 * frontend level updates
	 * 
	 * @param status
	 */
	private static void localUpdate(String status) {

		String originalStr = textArea.getText();
		textArea.setText(originalStr + "" + status + " ] ");
		logger.log(status);
		// statusBar.setText(status);

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