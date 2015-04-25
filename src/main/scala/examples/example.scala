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
  val g=s.graph

  def updateGraph(): Unit ={
    import scala.util.Random
    println("Updating graph en GraphSource: "+i)
    val nodeId="n"+i.toString
    val node=jsLit(id = nodeId, label =nodeId ,x = i % 7, y = i % 11, size = 1, color = "yellow")
    g.addNode(node)
    if(i>0) {
      val idSrc="n"+Random.nextInt(i)
      println("idSrc: "+idSrc)
      val edge = jsLit(id = i.toString, source = idSrc, target = nodeId)
      g.addEdge(edge)
    }
    i+=1
    s.refresh()

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

    s1.graph.addNode(jsLit(id = "n1", label = "Hellow", size = 1, x = 0, y = 0)).
      addNode(jsLit(id = "n2", labe1 = "World", size = 1, x = 1, y = 1)).
      addEdge(jsLit(id = "e", source = "n1", target = "n2"))

    s1.refresh()
  }

  @JSExport
  def force(target: html.Element): Unit = {
    val s = Sigma(target)



    val gSource=new GraphSource(s)
    var running=false

    target.onmousedown=(e: dom.MouseEvent)=>{
      println("mouse clin at"+e.button)
      e.button match {
        case 0 => {
          if(running) s.killForceAtlas2()
          gSource.updateGraph
          if(running) s.startForceAtlas2()
        }
        case 1=>if( ! running ) {
            running=true
            s.startForceAtlas2()
          }
        case 2=>if( running ) {
          s.killForceAtlas2()
          running=false
        }
        case _=>
      }


    }

  }
}