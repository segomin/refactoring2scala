package org.sangho.refac2scala
package ch01

class Statement {
  def statement(invoice: Invoice, plays: Plays): String = {
    renderPlainText(StatementData(invoice, plays))
  }

  def htmlStatement(invoice: Invoice, plays: Plays): String = {
    renderHtml(StatementData(invoice, plays))
  }

  def renderPlainText(data: StatementData): String = {
    val result = new StringBuilder(s"청구내역 (고객명: ${data.customer})\n")

    for (performance <- data.performances) {
      // 청구 내역을 출력한다.
      result.append(s"${performance.play.name}: ${performance.amount.usd} (${performance.audience}석)\n")
    }

    result.append(s"총액: ${data.totalAmount.usd}\n")
    result.append(s"적립 포인트: ${data.totalVolumeCredits}점")
    result.toString
  }

  def renderHtml(data: StatementData): String = {
    val result = new StringBuilder(s"<h1>청구내역 (고객명: ${data.customer})</h1>\n")
    result.append("<table>\n")
    result.append("<tr><th>연극</th><th>좌석수</th><th>금액</th></tr>\n")
    for (performance <- data.performances) {
      result.append(s"<tr><td>${performance.play.name}</td><td>${performance.audience}석</td><td>${performance.amount.usd}</td></tr>\n")
    }
    result.append("</table>\n")
    result.append(s"<p>총액: <em>${data.totalAmount.usd}</em></p>\n")
    result.append(s"<p>적립 포인트: <em>${data.totalVolumeCredits}</em>점</p>")
    result.toString
  }

  extension (amount: Int)
    private def usd: String = "$%,.2f".format((amount / 100).toDouble)
}

