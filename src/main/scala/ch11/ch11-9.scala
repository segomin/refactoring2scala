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

class Score (candidate: Candidate, medicalExam: MedicalExam, scoringGuide: ScoringGuide) {
  private var result = 0
  private var healthLevel = 0
  private var highMedicalRiskFlag = false

  def execute(): Int = {
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
}

def score(candidate: Candidate, medicalExam: MedicalExam, scoringGuide: ScoringGuide): Int = {
  new Score(candidate, medicalExam, scoringGuide).execute()
}

// main
@main def main(): Unit = {
  val scoringGuide = new ScoringGuide()
  assert(score(Candidate("low"), MedicalExam(true), scoringGuide) == -10)
  assert(score(Candidate("low"), MedicalExam(false), scoringGuide) == -5)
  assert(score(Candidate("high"), MedicalExam(true), scoringGuide) == -5)
  assert(score(Candidate("high"), MedicalExam(false), scoringGuide) == 0)
}
