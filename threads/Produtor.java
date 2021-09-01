package threads;
import javax.swing.JLabel;
import static tela.Controladora.mutex;
import static tela.Controladora.vazio;
import static tela.Controladora.contador;
import static tela.Controladora.cheio;
import static tela.Controladora.produtos;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Euler da Silva Lima
Matricula: 201911534
Inicio...: 16 de maio de 2021
Alteracao: 23 de maio de 2021
Nome.....: Produtor.java
Funcao...: Classe Thread do produtor, definindo as regras de producao.
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
public class Produtor extends Thread {

  //Variaveis de velocidade, e labels das imagens referentes ao produtor.
  public static int velocidadeprodutor = 200;
  private JLabel produtorvoltando = new JLabel();
  private JLabel produtorindo = new JLabel(); 
  private boolean voltando = false;

  public void run(){    
    //Aqui controlamos a animacao das labels.
    while (true){
      this.controle();  
      try{
        sleep(velocidadeprodutor);
        if(!voltando){;
          produtorvoltando.setBounds(produtorvoltando.getX() + 1, produtorvoltando.getY(), 60,38);
          produtorindo.setBounds(produtorindo.getX() + 1, produtorindo.getY(), 60,38);
        }else{
          produtorvoltando.setBounds((produtorvoltando.getX() - 1) , produtorvoltando.getY(), 60,38);
          produtorindo.setBounds(produtorindo.getX() - 1, produtorindo.getY(), 60,38);
        }
      }catch(Exception e){}
    }
  }
  
  /********************************************************************* 
  * Metodo: controle.
  * Funcao: Controla a posicao em que o produtor insere o produto no buffer, controlado por um semaphore, alem de controlar algumas animacoes.
  * Parametros: Nenhum.
  * Retorno: void.
  ********************************************************************* */
  public void controle(){
    if(this.produtorvoltando.getX() == 100){ //Quando chega perto do buffer, acionamos os semaforos.
      try{
        vazio.acquire();
        mutex.acquire();       
        produtos.add(this.produzir()); //Adicionamos no buffer o produto, quando o semaforo permitir.
        contador.setText("Buffer " + produtos.size() + "/" + "5");
      }catch(InterruptedException e){
      }finally{
        this.voltando = true;
        this.produtorvoltando.setVisible(true);
        this.produtorindo.setVisible(false);
        //Liberamos os semaforos.
        mutex.release();
        cheio.release();
      }      
    }else if(this.produtorvoltando.getX() == 50){
      this.voltando = false;
      this.produtorvoltando.setVisible(false);
      this.produtorindo.setVisible(true);
    }
  }
  
  /********************************************************************* 
  * Metodo: produzir.
  * Funcao: Simula um produto produzido pelo produtor.
  * Parametros: Nenhum.
  * Retorno: int.
  ********************************************************************* */
  public int produzir(){
    return 1;  
  }

  public JLabel getprodutorvoltando() {
    return this.produtorvoltando;
  }

  public void setprodutorvoltando(JLabel produtorvoltando) {
    this.produtorvoltando = produtorvoltando;
  }

  public JLabel getprodutorindo() {
    return this.produtorindo;
  }

  public void setprodutorindo(JLabel produtorindo) {
    this.produtorindo = produtorindo;
  }

}