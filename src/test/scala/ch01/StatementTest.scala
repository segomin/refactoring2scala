package org.sangho.refac2scala
package ch01

class StatementTest extends munit.FunSuite:

  test("chapter 1 test") {
    val hamlet = Performance("hamlet", 55)
    val asLike = Performance("as-like", 35)
    val othello = Performance("othello", 40)
    val plays = Plays(
      (hamlet, Play("Hamlet", PlayType.TRAGEDY)),
      (asLike, Play("As You Like It", PlayType.COMEDY)),
      (othello, Play("Othello", PlayType.TRAGEDY))
    )
    val str = Statement().statement(
      Invoice("BigCo", List(hamlet, asLike, othello)),
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
