package io.jvm.jsad.test

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest._
import io.jvm.jsad.RunnerDirect

@RunWith(classOf[JUnitRunner])
class RunnerTest extends FeatureSpec with GivenWhenThen with MustMatchers {
  feature("Runner Test"){

    scenario("Cat") {
      val r = new RunnerDirect()
      val params : Array[String] = Array("cat")

      val out = r.exec(params, params(0).getBytes)

//      println("out.code=" + out.code)
//      println("out.output=" + new String(out.output))

      out.code must be (0)
      new String(out.output) must be ("cat")
    }

    scenario("WordCount") {
      val r = new RunnerDirect()
      val params : Array[String] = Array("wc")

      val out = r.exec(params, "word1 word2 word3 word4".getBytes)

      println("out.code=" + out.code)
      println("out.output=" + new String(out.output))

      out.code must be (0)
      //new String(out.output) must be ("cat")
    }

    scenario("Echo") {
      val r = new RunnerDirect()
      val echo = "1234 5678 90"
      val params : Array[String] = Array("echo", echo )

      val out = r.exec(params)

      println("out.code=" + out.code)
      println("out.output=" + new String(out.output))

      out.code must be (0)
      assert(new String(out.output).startsWith(echo))
    }

  }

}
