package scala.js.sigma

/**
 * Created by gil on 23/04/15.
 */

import org.scalajs.dom

import scala.collection.immutable.HashMap
import scala.scalajs.js
import js.annotation.JSName
import org.scalajs.dom.html
import js.DynamicImplicits
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
    println("added node: "+n)
    sigmaJS.refresh()
    if (isRunning) sigmaJS.startForceAtlas2()
    this
  }

  def addEdge(e: js.Dynamic): Sigma = {
    if (isRunning) sigmaJS.killForceAtlas2()
    graphJS.addEdge(e)
    println("added edge: "+e)
    sigmaJS.refresh()
    if (isRunning) sigmaJS.startForceAtlas2()
    this
  }

  def startForce(config: js.Dynamic = jsLit(worker = true, barnesHutOptimize = false)): Unit = {
    sigmaJS.startForceAtlas2()
    isRunning = true
  }

  def stopForce(): Unit = {
    sigmaJS.killForceAtlas2()
    isRunning = false
  }



}

object SigmaJS  {
  def apply(target:html.Element): SigmaJS = jsNew(jsGlo.sigma)(target).asInstanceOf[SigmaJS]
}

trait GraphJS extends js.Object {
  def addNode(n: js.Dynamic): GraphJS = js.native

  def addEdge(e: js.Dynamic): GraphJS = js.native

  def nodes(): js.Array[js.Dynamic] = js.native

  def edges(): js.Array[js.Dynamic] = js.native
}

trait SigmaJS extends js.Object {

  def graph:GraphJS=js.native

  def refresh(): Unit = js.native

  def startForceAtlas2(config: js.Dynamic = jsLit(worker = true, barnesHutOptimize = false)): Unit = js.native

  def killForceAtlas2(): Unit = js.native


}









