package org.sangho.refac2scala
package ch01

class StatementTest extends munit.FunSuite:
  val plays: Plays = Plays(
    ("hamlet", Play("Hamlet", PlayType.TRAGEDY)),
    ("as-like", Play("As You Like It", PlayType.COMEDY)),
    ("othello", Play("Othello", PlayType.TRAGEDY))
  )
  val invoice: Invoice = Invoice("BigCo",
    List(Performance("hamlet", 55),
      Performance("as-like", 35),
      Performance("othello", 40)))

  test("render plain text") {
    val str = Statement().statement(
      invoice,
      plays
    )
    val expect =
      """청구내역 (고객명: BigCo)
        |Hamlet: $650.00 (55석)
        |As You Like It: $580.00 (35석)
        |Othello: $500.00 (40석)
        |총액: $1,730.00
        |적립 포인트: 47점""".stripMargin
    assertEquals(str, expect)
  }

  test("render html text") {
    val str = Statement().htmlStatement(
      invoice,
      plays
    )
    val expect =
      """<h1>청구내역 (고객명: BigCo)</h1>
        |<table>
        |<tr><th>연극</th><th>좌석수</th><th>금액</th></tr>
        |<tr><td>Hamlet</td><td>55석</td><td>$650.00</td></tr>
        |<tr><td>As You Like It</td><td>35석</td><td>$580.00</td></tr>
        |<tr><td>Othello</td><td>40석</td><td>$500.00</td></tr>
        |</table>
        |<p>총액: <em>$1,730.00</em></p>
        |<p>적립 포인트: <em>47</em>점</p>""".stripMargin
    assertEquals(str, expect)
  }
