package threads;
import javax.swing.JLabel;
import static tela.Controladora.mutex;
import static tela.Controladora.vazio;
import static tela.Controladora.cheio;
import static tela.Controladora.produtos;
import static tela.Controladora.contador;
import static tela.Controladora.encomendas;
import static tela.Controladora.totalencomendas;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Euler da Silva Lima
Matricula: 201911534
Inicio...: 16 de maio de 2021
Alteracao: 23 de maio de 2021
Nome.....: Consumidor.java
Funcao...: Classe Thread do consumidor, definindo as regras de consumo.
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
public class Consumidor extends Thread{

  //Variaveis de velocidade, e labels das imagens referentes ao consumidor.
  public static int velocidadeconsumidor = 200;
  private JLabel consumidorvoltando = new JLabel();
  private JLabel consumidorindo = new JLabel();
  private int encomenda;
  private boolean voltando = true;

  public void run(){
    this.consumidorvoltando.setVisible(true);
    this.consumidorindo.setVisible(false);
    //Aqui controlamos a animacao das labels.
    while (true){
      this.controle();      
     try{
        sleep(velocidadeconsumidor);
        if(!voltando){
          consumidorvoltando.setBounds(consumidorvoltando.getX() + 1, consumidorvoltando.getY(), 60,38);
          consumidorindo.setBounds(consumidorindo.getX() + 1, consumidorindo.getY(), 60,38);
        }else{
          consumidorvoltando.setBounds((consumidorvoltando.getX() - 1) , consumidorvoltando.getY(), 60,38);
          consumidorindo.setBounds(consumidorindo.getX() - 1, consumidorindo.getY(), 60,38);
        }
      }catch(Exception e){}
    }
  }  
  /********************************************************************* 
  * Metodo: controle.
  * Funcao: Controla a posicao em que o consumidor retira o produto no buffer, controlado por um semaphore, alem de controlar algumas animacoes.
  * Parametros: Nenhum.
  * Retorno: void.
  ********************************************************************* */
  public void controle(){
    if(this.consumidorvoltando.getX() == 350){ 
      this.consumir();
      encomendas.setText("Total encomendas: " + totalencomendas);
      this.voltando = true;
      this.consumidorvoltando.setVisible(true);
      this.consumidorindo.setVisible(false); 
    }else if(this.consumidorvoltando.getX() == 250){ //Quando chega perto do buffer, acionamos os semaforos.
      try{
      cheio.acquire();
      mutex.acquire();
      this.removeitem(); //Retiramos do buffer o produto, quando o semaforo permitir.
      contador.setText("Buffer " + produtos.size() + "/" + "5");
      }catch(InterruptedException e){
      }finally{
      //Liberamos os semaforos.
      mutex.release();
      vazio.release();
      this.voltando = false;
      this.consumidorvoltando.setVisible(false);
      this.consumidorindo.setVisible(true);
      }
    }
  }
  /********************************************************************* 
  * Metodo: removeitem.
  * Funcao: Remove item do buffer.
  * Parametros: Nenhum.
  * Retorno: void.
  ********************************************************************* */
  private void removeitem(){
    this.encomenda = produtos.remove(0);
  }

  /********************************************************************* 
  * Metodo: consumir.
  * Funcao: Adiciona valor retirado no buffer em um contador e atualiza na tela.
  * Parametros: Nenhum.
  * Retorno: void.
  ********************************************************************* */
  private void consumir(){
    totalencomendas += this.encomenda;
    
  }

  public JLabel getconsumidorvoltando() {
    return this.consumidorvoltando;
  }
  public void setconsumidorvoltando(JLabel consumidorvoltando) {
    this.consumidorvoltando = consumidorvoltando;
  }

  public JLabel getconsumidorindo() {
    return this.consumidorindo;
  }

  public void setconsumidorindo(JLabel consumidorindo) {
    this.consumidorindo = consumidorindo;
  }  
}
