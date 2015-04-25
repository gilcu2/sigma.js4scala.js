/**
 * Created by gil on 4/24/15.
 */

package examples

import scala.js
import org.scalajs.dom
import dom.html
import js.sigma._
import scala.scalajs.js.annotation.JSExport

import scala.scalajs.js.Dynamic.{global => jsGlo, newInstance => jsNew, literal => jsLit}

class GraphSource(val s:Sigma) {
  var i=0
  var j=0

  def updateGraph(): Unit ={
    import scala.util.Random
    println("Updating graph en GraphSource: "+i)

    val nodeId="n"+i.toString
    val node=jsLit(id = nodeId, label =nodeId ,x = Random.nextInt(10), y = Random.nextInt(10), size = 1, color = "blue")
    s.addNode(node)
    if(i>0) {
      val idSrc="n"+Random.nextInt(i)
      println("idSrc: "+idSrc)
      val edge = jsLit(id = i.toString, source = idSrc, target = nodeId)
      s.addEdge(edge)
    }
    i+=1

  }

  def repeatUpdate(): Unit ={
    updateGraph()
    j+=1
    if(j<20)
      dom.setTimeout(()=>this.repeatUpdate,1000)
  }
}

@JSExport
object Example {

  @JSExport
  def basic(target: html.Element): Unit = {

    val s1 = Sigma(target)

    s1.addNode(jsLit(id = "n1", label = "Hellow", size = 1, x = 0, y = 0)).
      addNode(jsLit(id = "n2", labe1 = "World", size = 1, x = 1, y = 1)).
      addEdge(jsLit(id = "e", source = "n1", target = "n2"))

  }

  @JSExport
  def force(target: html.Element): Unit = {
    val s = Sigma(target)
    val gSource=new GraphSource(s)
    s.startForce()

    target.onmousedown=(e: dom.MouseEvent)=>{
      println("mouseDown with: "+e.button)
      e.button match {
        case 0 => gSource.updateGraph
        case 1=>/*s.startForce()*/
        case 2=>s.stopForce()
        case _=>
      }


    }

  }
}