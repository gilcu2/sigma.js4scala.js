package scala.js.sigma

/**
 * Created by gil on 23/04/15.
 */

import scala.scalajs.js
import js.annotation.JSName
import org.scalajs.dom.html
import js.DynamicImplicits
import js.Dynamic.{global => jsGlo, newInstance => jsNew, literal => jsLit}


object Sigma  {
  def apply(target:html.Element): Sigma = jsNew(jsGlo.sigma)(target).asInstanceOf[Sigma]
}

trait Sigma extends js.Object {
  private var forceRunning=false
  def graph:Graph=js.native
  def refresh():Unit=js.native

  private def startForceAtlas2(config:js.Dynamic=jsLit(worker=true, barnesHutOptimize=false)):Unit=js.native
  private def stopForceAtlas2():Unit=js.native
  private def killForceAtlas2():Unit=js.native

  def startForce(config:js.Dynamic=jsLit(worker=true, barnesHutOptimize=false)):Unit={
    this.startForceAtlas2()
    forceRunning=true
  }

  def stopForce(): Unit ={
    this.stopForceAtlas2()
    forceRunning=false
  }

  def killForce(): Unit ={
    this.killForceAtlas2()
    forceRunning=false
  }

  def isForceRunning():Boolean=forceRunning

}

trait Graph extends js.Object {
  private def addNode(n:js.Dynamic):Graph=js.native
  private def addEdge(e:js.Dynamic):Graph=js.native
  def nodes():js.Array[js.Dynamic]=js.native
  def edges():js.Array[js.Dynamic]=js.native
}







