package org.sangho.refac2scala
package ch11_9



case class Candidate(originState: String) {
}
case class MedicalExam(isSmoker: Boolean) {
}

class ScoringGuide {
  def stateWithLowCertification(state: String): Boolean = {
    state == "low"
  }
}

def score(candidate: Candidate, medicalExam: MedicalExam, scoringGuide: ScoringGuide): Int = {
  var result = 0
  var healthLevel = 0
  var highMedicalRiskFlag = false

  if (medicalExam.isSmoker) {
    healthLevel += 10
    highMedicalRiskFlag = true
  }
  var certificationGrade = "regular"
  if (scoringGuide.stateWithLowCertification(candidate.originState)) {
    certificationGrade = "low"
    result -= 5
  }
  // 비슷한 코드가 한참 이어짐
  result -= Math.max(healthLevel - 5, 0)
  result
}

// main
@main def main(): Unit = {
  val scoringGuide = new ScoringGuide()
  assert(score(Candidate("low"), MedicalExam(true), scoringGuide) == -10)
  assert(score(Candidate("low"), MedicalExam(false), scoringGuide) == -5)
  assert(score(Candidate("high"), MedicalExam(true), scoringGuide) == -5)
  assert(score(Candidate("high"), MedicalExam(false), scoringGuide) == 0)
}
