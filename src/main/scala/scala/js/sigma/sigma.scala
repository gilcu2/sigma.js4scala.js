package scala.js.sigma

/**
 * Created by gil on 23/04/15.
 */

import scala.scalajs.js
import js.annotation.JSName
import org.scalajs.dom.html

import js.Dynamic.{global => jsGlo, newInstance => jsNew, literal => jsLit}


object Sigma  {
  def apply(target:html.Element): Sigma = jsNew(jsGlo.sigma)(target).asInstanceOf[Sigma]
}

trait Sigma extends js.Object {
  def graph:Graph=js.native
  def refresh():Unit=js.native
  def startForceAtlas2():Unit=js.native
  def stopForceAtlas2():Unit=js.native
  def killForceAtlas2():Unit=js.native
  def isForceAtlas2Running(config:js.Dynamic=jsLit(worker=true, barnesHutOptimize=false)):Unit=js.native
}

trait Graph extends js.Object {
  def addNode(n:js.Dynamic):Graph=js.native
  def addEdge(e:js.Dynamic):Graph=js.native
  def nodes():js.Array[js.Dynamic]=js.native
  def edges():js.Array[js.Dynamic]=js.native
}







