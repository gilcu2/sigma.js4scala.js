package scala.js.sigma

/**
 * Created by gil on 23/04/15.
 */

import org.scalajs.dom


import scala.scalajs.js
import js.annotation.JSName
import org.scalajs.dom.html
import js.Dynamic.{global => jsGlo, newInstance => jsNew, literal => jsLit}


object Sigma  {
  def apply(target:html.Element): Sigma = new Sigma(target)
}

class Sigma(target:html.Element) {

  private var isRunning = false

  private val sigmaJS=SigmaJS(target)
  private val graphJS=sigmaJS.graph

  def addNode(n: js.Dynamic): Sigma = {
    if (isRunning) sigmaJS.killForceAtlas2()
    graphJS.addNode(n)
    sigmaJS.refresh()
    if (isRunning)   startForce()
    this
  }

  def addEdge(e: js.Dynamic): Sigma = {
    if (isRunning) sigmaJS.killForceAtlas2()
    graphJS.addEdge(e)
    sigmaJS.refresh()
    if (isRunning) startForce()
    this
  }

  def rmNode(id:String): Sigma = {
    if (isRunning) sigmaJS.killForceAtlas2()
    graphJS.rmNode(id)
    sigmaJS.refresh()
    if (isRunning)   startForce()
    this
  }

  def rmEdge(id:String): Sigma = {
    if (isRunning) sigmaJS.killForceAtlas2()
    graphJS.rmEdge(id)
    sigmaJS.refresh()
    if (isRunning) startForce()
    this
  }

  def startForce(config: js.Dynamic = jsLit(worker = true, barnesHutOptimize = false)): Unit = {
    sigmaJS.startForceAtlas2()
    dom.setTimeout(() => this.checkForceConvergency, 1000)
    isRunning = true
  }

  def stopForce(): Unit = {
    sigmaJS.killForceAtlas2()
    isRunning = false
  }

  def isForceRunning(): Boolean = isRunning

  var oldPosis=Map[String,PR2]()
  private def checkForceConvergency(): Unit = {

    if(! isRunning) return

    val newPosis=graphJS.nodes.map(node=>node.id.asInstanceOf[String]->new PR2(node.x.asInstanceOf[Double],node.y.asInstanceOf[Double])).toMap

    val newSize=newPosis.size
    if(newSize != oldPosis.size) {
      oldPosis=newPosis
      dom.setTimeout(() => this.checkForceConvergency, 1000)
      return
    }


    var sumDiff:Double=0.0
    for(i <- newPosis.keys) {
      val oldPosOpt=oldPosis.get(i)
      oldPosOpt match {
        case Some(oldPos)=>{
          val newPos=newPosis(i)
          sumDiff+=(newPos-oldPos).abs2
        }
        case _ =>{
          oldPosis=newPosis
          return
        }
      }
    }

    oldPosis = newPosis
    if(sumDiff/newSize>1.0  )
      dom.setTimeout(() => this.checkForceConvergency, 1000)
    else
      sigmaJS.killForceAtlas2
  }

}

class PR2(val x:Double,val y:Double) {
  def - (p:PR2):PR2=new PR2(x-p.x,y-p.y)
  def abs2():Double=x*x+y*y
  override def toString()="("+x+","+y+")"
}

object SigmaJS  {
  def apply(target:html.Element): SigmaJS = jsNew(jsGlo.sigma)(target).asInstanceOf[SigmaJS]
}

trait GraphJS extends js.Object {

  def addNode(n: js.Dynamic): GraphJS = js.native

  def addEdge(e: js.Dynamic): GraphJS = js.native

  def nodes(): js.Array[js.Dynamic] = js.native

  def edges(): js.Array[js.Dynamic] = js.native

  @JSName("dropNode")
  def rmNode(id:String):Unit=js.native

  @JSName("dropEdge")
  def rmEdge(id:String):Unit=js.native

}

trait SigmaJS extends js.Object {

  def graph:GraphJS=js.native

  def refresh(): Unit = js.native

  def startForceAtlas2(config: js.Dynamic = jsLit(worker = true, barnesHutOptimize = false)): Unit = js.native

  def killForceAtlas2(): Unit = js.native


}









