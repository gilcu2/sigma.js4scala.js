/**
 * Created by gil on 4/24/15.
 */

package scala.js.sigma

import scala.collection.mutable
import scala.js
import org.scalajs.dom
import dom.html
import js.sigma._
import scala.scalajs.js.annotation.JSExport

import scala.scalajs.js.Dynamic.{global => jsGlo, newInstance => jsNew, literal => jsLit}

class GraphSource(val s:Sigma) {
  import scala.util.Random

  var i=0
  var j=0
  var repeat=false

  val idNodes=mutable.ListBuffer[String]()
  val idEdges=mutable.ListBuffer[String]()


  def updateGraph(): Unit ={
    val opt=Random.nextInt(4)
    opt match {
      case 0=>addNode()
      case 1=>if(idNodes.size>3) addEdge() else addNode()
      case 2=>if(idNodes.size>5) rmNode() else addNode()
      case 3=>if(idEdges.size>5) rmEdge() else if(idNodes.size>3) addEdge() else addNode()
    }
  }

  def addNode():Unit={
    println("beg add node")
    val nodeId="n"+i
    idNodes+=(nodeId)
    val nodeLabel=if(Random.nextBoolean()) nodeId else ""
    val node=jsLit(id = nodeId, label =nodeLabel ,x = Random.nextInt(10), y = Random.nextInt(10),
         size=Random.nextInt(3)+1,color = "#617db4")
    s.addNode(node)
    i+=1
    println("added node: "+nodeId)
  }

  def addEdge():Unit={
    println("beg add edge")
    val inSrc=Random.nextInt(idNodes.size)
    var inDst=Random.nextInt(idNodes.size)
    if(inDst==inSrc) inDst=(inDst+1)%idNodes.size
    val edge = jsLit(id ="e"+i ,label="e"+i, source = idNodes(inSrc), target = idNodes(inDst), color="#b956af")
    s.addEdge(edge)
    idEdges+=(i.toString)
    i+=1
    println("added edge: "+i)
  }

  def rmNode(): Unit ={
    println("beg rm node from "+idNodes.size)
    val ind=Random.nextInt(idNodes.size)
    println("ind "+ind)
    val id=idNodes(ind)
    println("try  rm node "+id)
    try {
      s.rmNode(id)
    } catch {
      case  x: Throwable=>println("Exception removing node: "+x)
    }
    idNodes-=(id)
    println("rm node: "+id)
  }

  def rmEdge(): Unit ={
    println("beg rm edge")
    val id=idEdges(Random.nextInt(idEdges.size))
    try {
      s.rmEdge(id)
    } catch {
      case  x: Throwable=>println("Exception removing edge: "+x)
    }
    idEdges-=(id)
    println("rm edge: "+id)
  }

  def repeatUpdate(): Unit ={
    updateGraph()
    j+=1
    if(repeat)
      dom.setTimeout(()=>this.repeatUpdate,1000)
  }
}

@JSExport
object Examples {

  @JSExport
  def basic(target: html.Element): Unit = {

    val s1 = Sigma(target)

    s1.addNode(jsLit(id="n1", label = "Hellow", size = 1, x = 0, y = 0)).
      addNode(jsLit(id = "n2", labe1 = "World", size = 1, x = 1, y = 1)).
      addEdge(jsLit(id = "e", source = "n1", target = "n2"))

  }

  @JSExport
  def force(target: html.Element): Unit = {
    val s = Sigma(target)
    val gSource=new GraphSource(s)
    s.startForce()
    gSource.repeat=true
    gSource.repeatUpdate()

    target.onmousedown=(e: dom.MouseEvent)=>{
      println("mouseDown with: "+e.button)
      e.button match {
        case 0 => {
          gSource.repeat= ! gSource.repeat
          gSource.repeatUpdate()
        }
        case 2=>/*s.startForce()*/
        case 1=>s.stopForce()
        case _=>
      }


    }

  }
}