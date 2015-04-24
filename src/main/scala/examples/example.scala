/**
 * Created by gil on 4/24/15.
 */

package examples

import scala.js
import org.scalajs.dom
import js.sigma._
import scala.scalajs.js.annotation.JSExport

import scala.scalajs.js.Dynamic.{global => jsGlo, newInstance => jsNew, literal => jsLit}

@JSExport
object Example {

  @JSExport
  def basic(target: dom.html.Element): Unit = {

    val s1 = Sigma(target)

    s1.graph.addNode(jsLit(id = "n1", label = "Hellow", size = 1, x = 0, y = 0)).
      addNode(jsLit(id = "n2", labe1 = "World", size = 1, x = 1, y = 1)).
      addEdge(jsLit(id = "e", source = "n1", target = "n2"))

    s1.refresh()
  }
}