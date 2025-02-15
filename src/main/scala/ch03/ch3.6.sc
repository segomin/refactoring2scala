//3.6 Mutable Data

// before
class Team1 {
  var members: List[String] = List("Alice", "Bob", "Charlie")

  def getMembers: List[String] = members
}

val team1 = new Team1()
team1.getMembers :+ "David" // Directly modifying the list
println(team1.getMembers) // Output: List(Alice, Bob, Charlie, David)

// after
class Team2 {
  private var members: List[String] = List("Alice", "Bob", "Charlie")

  def getMembers: List[String] = members

  def addMember(member: String): Unit = {
    members = members :+ member
  }

  def removeMember(member: String): Unit = {
    members = members.filterNot(_ == member)
  }
}

val team2 = new Team2()
team2.addMember("David")
println(team2.getMembers) // Output: List(Alice, Bob, Charlie, David)
team2.removeMember("Bob")
println(team2.getMembers) // Output: List(Alice, Charlie, David)