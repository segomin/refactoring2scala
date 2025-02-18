//3.4 Long Parameter List
case class User(firstName: String, lastName: String, age: Int, email: String)

// before
def createUser(firstName: String, lastName: String, age: Int, email: String): User = {
  User(firstName, lastName, age, email)
}
// after
case class UserInfo(firstName: String, lastName: String, age: Int, email: String)

def createUser(userInfo: UserInfo): User = {
  User(userInfo.firstName, userInfo.lastName, userInfo.age, userInfo.email)
}

// Usage
val userInfo = UserInfo("Sang", "Ho", 30, "abc@a.c")
val user = createUser(userInfo)
assert(user == User("Sang", "Ho", 30, "abc@a.c"))