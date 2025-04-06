package ch12_10_2

import org.scalatest.Assertions.*

case class Data(name: String,
                kind: String,
                plumage: String = "보통이다",
                numberOfCoconuts: Int = 0,
                voltage: Double = 0.0,
                isNailed: Boolean = false) {
}

def createBird(data: Data): Bird = {
  data.kind match {
    case _ => new Bird(data)
  }
}

class Bird(data: Data) {
  val name: String = data.name
  private val _plumage: String = if (data.plumage == null) "보통이다" else data.plumage
  val _speciesDelegate: BirdDelegate = selectSpeciesDelegate(data)
  def airSpeedVelocity: Double = if (_speciesDelegate != null)
    _speciesDelegate.airSpeedVelocity
  else
    0.0
  def plumage: String = _speciesDelegate match {
    case value: NorwegianBlueParrotDelegate => value.plumage
    case _ => _plumage
  }

  private def selectSpeciesDelegate(data: Data): BirdDelegate = {
    data.kind match {
      case "유럽 제비" => new EuropeanSwallowDelegate(data)
      case "아프리카 제비" => new AfricanSwallowDelegate(data)
      case "노르웨이 파랑 앵무" => new NorwegianBlueParrotDelegate(data)
      case _ => null
    }
  }
}

trait BirdDelegate {
  def airSpeedVelocity: Double
}

class EuropeanSwallowDelegate(data: Data) extends BirdDelegate{
  override def airSpeedVelocity: Double = 35.0
}

class AfricanSwallowDelegate(data: Data) extends BirdDelegate {
  val numberOfCoconuts: Int = data.numberOfCoconuts
  override def airSpeedVelocity: Double = 40.0 - 2.0 * data.numberOfCoconuts
}

class NorwegianBlueParrotDelegate(data: Data) extends BirdDelegate {
  def plumage: String = if (data.voltage > 100)
    "그을렸다"
  else if (data.plumage == null)
    "예쁘다"
  else
    data.plumage

  override def airSpeedVelocity: Double = if (data.isNailed)
    0.0
  else
    10.0 + data.voltage / 10.0
}

// main
@main def main() = {
  val europeanSwallow = createBird(Data("EU", "유럽 제비"))
  val africanSwallow = createBird(Data("AF", "아프리카 제비", numberOfCoconuts = 2))
  val norwegianBlueParrot = createBird(Data("NOR", "노르웨이 파랑 앵무", voltage = 120.0, isNailed = false))
  val norwegianBlueParrot2 = createBird(Data("NOR", "노르웨이 파랑 앵무", voltage = 120.0, isNailed = true))
  assertResult("EU")(europeanSwallow.name)
  assertResult(35.0)(europeanSwallow.airSpeedVelocity)
  assertResult("보통이다")(europeanSwallow.plumage)
  assertResult("AF")(africanSwallow.name)
  assertResult(36.0)(africanSwallow.airSpeedVelocity)
  assertResult("그을렸다")(norwegianBlueParrot.plumage)
  assertResult(22.0)(norwegianBlueParrot.airSpeedVelocity)
  assertResult(22.0)(norwegianBlueParrot.airSpeedVelocity)
  assertResult(0.0)(norwegianBlueParrot2.airSpeedVelocity)
}
