package tela;
import threads.Consumidor;
import threads.Produtor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import static threads.Produtor.velocidadeprodutor;
import static threads.Consumidor.velocidadeconsumidor;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.awt.event.*;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Euler da Silva Lima
Matricula: 201911534
Inicio...: 16 de maio de 2021
Alteracao: 23 de maio de 2021
Nome.....: Controladora.java
Funcao...: Classe de controle, onde o que aparece na tela e os processos sao controlados.
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
public class Controladora {
  private JFrame janela = new JFrame("Problema produtor/produtor");
  //Carros dos produtores.
  private JLabel fiorindo = new JLabel();
  private JLabel fiovoltano = new JLabel();
  private JLabel fiorindo2 = new JLabel();
  private JLabel fiovoltano2 = new JLabel();
  private JLabel fiorindo3 = new JLabel();
  private JLabel fiovoltano3 = new JLabel();
  private JLabel fiorindo4 = new JLabel();
  private JLabel fiovoltano4 = new JLabel();
  private JLabel fiorindo5 = new JLabel();
  private JLabel fiovoltano5 = new JLabel();

  //Carros dos consumidores.
  private JLabel fiorindo6 = new JLabel();
  private JLabel fiovoltano6 = new JLabel();
  private JLabel fiorindo7 = new JLabel();
  private JLabel fiovoltano7 = new JLabel();
  private JLabel fiorindo8 = new JLabel();
  private JLabel fiovoltano8 = new JLabel();
  private JLabel fiorindo9 = new JLabel();
  private JLabel fiovoltano9 = new JLabel();
  private JLabel fiorindo10 = new JLabel();
  private JLabel fiovoltano10 = new JLabel();

  //Label do fundo.
  private JLabel fundo = new JLabel();

  //Produtores.
  private Produtor produtor1 = new Produtor();
  private Produtor produtor2 = new Produtor();
  private Produtor produtor3 = new Produtor();
  private Produtor produtor4 = new Produtor();
  private Produtor produtor5 = new Produtor();

  //Consumidores.
  private Consumidor consumidor1 = new Consumidor();
  private Consumidor consumidor2 = new Consumidor();
  private Consumidor consumidor3 = new Consumidor();
  private Consumidor consumidor4 = new Consumidor();
  private Consumidor consumidor5 = new Consumidor();

  //Botoes.
  private JButton adicionar = new JButton("Adiocionar produtor");
  private JButton adicionarconsumidor = new JButton("Adicionar consumidor");

  //Listas.
  private List<Produtor> produtores = new ArrayList<Produtor>();
  private List<Consumidor> consumidores = new ArrayList<Consumidor>();
  public static List<Integer> produtos = new ArrayList<Integer>();
  //Fonte.
  String family = "Impact";
  int style = Font.PLAIN;
  int size = 18;
  Font f = new Font(family, style, size);

  String family2 = "Impact";
  int style2 = Font.PLAIN;
  int size2 = 12;
  Font f2 = new Font(family2, style2, size2);
  //Outros.
  private JTextField producao = new JTextField("Tempo de producao");
  public static JTextField contador = new JTextField();
  public static JTextField encomendas = new JTextField();
  public static int totalencomendas = 0;
  private JSlider velproducao = new JSlider(JSlider.HORIZONTAL, 50, 1000, 200);
  private JTextField consumo = new JTextField("Tempo de consumo");
  private JSlider velconsumo = new JSlider(JSlider.HORIZONTAL, 50, 1000, 200);
  private static int n = 5;
  public static Semaphore mutex = new Semaphore(1);
  public static Semaphore vazio = new Semaphore(n);
  public static Semaphore cheio = new Semaphore(0);
  public Controladora(){

    //Configurando janela.
    janela.setSize(800,350);
    janela.setResizable(false);
    janela.setLocationRelativeTo(null);
    janela.setVisible(true);
    janela.setDefaultCloseOperation(janela.EXIT_ON_CLOSE);
    janela.getContentPane().setLayout(null);

    //Botoes.
    adicionar.setVisible(true);
    adicionar.setBounds(500, 20, 250, 40);
    adicionar.setFont(f);
    adicionar.setForeground(Color.BLUE);
    adicionar.setBackground(Color.YELLOW);

    adicionarconsumidor.setVisible(true);
    adicionarconsumidor.setBounds(500, 62, 250, 40);
    adicionarconsumidor.setFont(f);
    adicionarconsumidor.setForeground(Color.BLUE);
    adicionarconsumidor.setBackground(Color.YELLOW);
    
    //Controle de velocidade de producao e consumo.
    producao.setEditable(false);
    producao.setBounds(510, 102, 250, 40);
    producao.setFont(f);
    velproducao.setBounds(510, 162, 250, 40);
    velproducao.setPaintTicks(true);
    velproducao.setMinorTickSpacing(100);
    velproducao.setPaintTrack(true);
    velproducao.setMajorTickSpacing(100);
    velproducao.setPaintLabels(true);

    consumo.setEditable(false);
    consumo.setBounds(510, 212, 250, 40);
    consumo.setFont(f);
    velconsumo.setBounds(510, 262, 250, 40);
    velconsumo.setPaintTicks(true);
    velconsumo.setMinorTickSpacing(100);
    velconsumo.setPaintTrack(true);
    velconsumo.setMajorTickSpacing(100);
    velconsumo.setPaintLabels(true);

    contador.setEditable(false);
    contador.setBounds(150, 60, 100, 20);
    contador.setFont(f2);
    contador.setText("Buffer " + produtos.size() + "/" + "5");

    encomendas.setEditable(false);
    encomendas.setBounds(370, 60, 140, 20);
    encomendas.setFont(f2);
    encomendas.setText("Total encomendas: " + totalencomendas);
    //Adicionando opcoes na tela.
    
    janela.add(adicionar);
    janela.add(adicionarconsumidor);
    janela.add(producao);
    janela.add(velproducao);
    janela.add(consumo);
    janela.add(velconsumo);
    janela.add(contador);
    janela.add(encomendas);

    //FUNDO
    fundo = new JLabel(new ImageIcon(getClass().getResource("/img/fundo.png")));
    fundo.setSize(500,300);

    //PRODUTORES
    fiorindo = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo.setBounds(50,70, 60, 38);
    produtor1.setprodutorvoltando(fiorindo);
    fundo.add(produtor1.getprodutorvoltando());
    fiovoltano.setBounds(50,70,60,38);
    produtor1.setprodutorindo(fiovoltano);
    fundo.add(produtor1.getprodutorindo());
    produtor1.getprodutorindo().setVisible(false);
    produtor1.getprodutorvoltando().setVisible(false);

    fiorindo2 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano2 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo2.setBounds(50,100, 60, 38);
    produtor2.setprodutorvoltando(fiorindo2);
    fundo.add(produtor2.getprodutorvoltando());
    fiovoltano2.setBounds(50,100,60,38);
    produtor2.setprodutorindo(fiovoltano2);
    fundo.add(produtor2.getprodutorindo());
    produtor2.getprodutorindo().setVisible(false);
    produtor2.getprodutorvoltando().setVisible(false);

    fiorindo3 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano3 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo3.setBounds(50,130, 60, 38);
    produtor3.setprodutorvoltando(fiorindo3);
    fundo.add(produtor3.getprodutorvoltando());
    fiovoltano3.setBounds(50,130,60,38);
    produtor3.setprodutorindo(fiovoltano3);
    fundo.add(produtor3.getprodutorindo());
    produtor3.getprodutorindo().setVisible(false);
    produtor3.getprodutorvoltando().setVisible(false);

    fiorindo4 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano4 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo4.setBounds(50,160, 60, 38);
    produtor4.setprodutorvoltando(fiorindo4);
    fundo.add(produtor4.getprodutorvoltando());
    fiovoltano4.setBounds(50,160,60,38);
    produtor4.setprodutorindo(fiovoltano4);
    fundo.add(produtor4.getprodutorindo());
    produtor4.getprodutorindo().setVisible(false);
    produtor4.getprodutorvoltando().setVisible(false);

    fiorindo5 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano5 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo5.setBounds(50,190, 60, 38);
    produtor5.setprodutorvoltando(fiorindo5);
    fundo.add(produtor5.getprodutorvoltando());
    fiovoltano5.setBounds(50,190,60,38);
    produtor5.setprodutorindo(fiovoltano5);
    fundo.add(produtor5.getprodutorindo());
    produtor5.getprodutorindo().setVisible(false);
    produtor5.getprodutorvoltando().setVisible(false);
    produtores.add(produtor1);
    produtores.add(produtor2);
    produtores.add(produtor3);
    produtores.add(produtor4);
    produtores.add(produtor5);

    //CONSUMIDORES

    fiorindo6 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano6 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo6.setBounds(300,70, 60, 38);
    consumidor1.setconsumidorvoltando(fiorindo6);
    fundo.add(consumidor1.getconsumidorvoltando());
    fiovoltano6.setBounds(300,70,60,38);
    consumidor1.setconsumidorindo(fiovoltano6);
    fundo.add(consumidor1.getconsumidorindo());
    consumidor1.getconsumidorindo().setVisible(false);
    consumidor1.getconsumidorvoltando().setVisible(false);

    fiorindo7 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano7 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo7.setBounds(300,100, 60, 38);
    consumidor2.setconsumidorvoltando(fiorindo7);
    fundo.add(consumidor2.getconsumidorvoltando());
    fiovoltano7.setBounds(300,100,60,38);
    consumidor2.setconsumidorindo(fiovoltano7);
    fundo.add(consumidor2.getconsumidorindo());
    consumidor2.getconsumidorindo().setVisible(false);
    consumidor2.getconsumidorvoltando().setVisible(false);

    fiorindo8 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano8 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo8.setBounds(300,130, 60, 38);
    consumidor3.setconsumidorvoltando(fiorindo8);
    fundo.add(consumidor3.getconsumidorvoltando());
    fiovoltano8.setBounds(300,130,60,38);
    consumidor3.setconsumidorindo(fiovoltano8);
    fundo.add(consumidor3.getconsumidorindo());
    consumidor3.getconsumidorindo().setVisible(false);
    consumidor3.getconsumidorvoltando().setVisible(false);

    fiorindo9 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano9 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo9.setBounds(300,160, 60, 38);
    consumidor4.setconsumidorvoltando(fiorindo9);
    fundo.add(consumidor4.getconsumidorvoltando());
    fiovoltano9.setBounds(300,160,60,38);
    consumidor4.setconsumidorindo(fiovoltano9);
    fundo.add(consumidor4.getconsumidorindo());
    consumidor4.getconsumidorindo().setVisible(false);
    consumidor4.getconsumidorvoltando().setVisible(false);

    fiorindo10 = new JLabel(new ImageIcon(getClass().getResource("/img/fiorindo.png")));
    fiovoltano10 = new JLabel(new ImageIcon(getClass().getResource("/img/fiovoltano.png")));
    fiorindo10.setBounds(300,190, 60, 38);
    consumidor5.setconsumidorvoltando(fiorindo10);
    fundo.add(consumidor5.getconsumidorvoltando());
    fiovoltano10.setBounds(300,190,60,38);
    consumidor5.setconsumidorindo(fiovoltano10);
    fundo.add(consumidor5.getconsumidorindo());
    consumidor5.getconsumidorindo().setVisible(false);
    consumidor5.getconsumidorvoltando().setVisible(false);

    consumidores.add(consumidor1);
    consumidores.add(consumidor2);
    consumidores.add(consumidor3);
    consumidores.add(consumidor4);
    consumidores.add(consumidor5);

    //Configuracao restante.
   
    fundo.setHorizontalAlignment(2);
    janela.add(fundo);
    janela.repaint();

    //Configuracoes dos botoes e sliders.    
    adicionar.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e){
        try{
        produtores.get(0).start();
        produtores.remove(0); 
        }catch(IndexOutOfBoundsException a){
        }
      }
    });

    adicionarconsumidor.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e){
        try{
        consumidores.get(0).start();
        consumidores.remove(0); 
        }catch(IndexOutOfBoundsException a){
        }
      }
    });

    velproducao.addChangeListener(new ChangeListener(){

      @Override
      public void stateChanged(ChangeEvent e) {
        int velocidadeproducao = velproducao.getValue();
        velocidadeprodutor = velocidadeproducao;
      }
      
    });

    velconsumo.addChangeListener(new ChangeListener(){

      @Override
      public void stateChanged(ChangeEvent e) {
        int velocidadeconsumo = velconsumo.getValue();
        velocidadeconsumidor = velocidadeconsumo;
      }
      
    });

  }
}
