//3.2 Duplicated Code

case class User(firstName: String, lastName: String)
type Admin = User

// before
def getUserName(user: User): String = {
  user.firstName + " " + user.lastName
}

def getAdminName(admin: Admin): String = {
  admin.firstName + " " + admin.lastName
}

// after
import scala.language.reflectiveCalls

def getFullName(person: { def firstName: String; def lastName: String }): String = {
  person.firstName + " " + person.lastName
}

// Usage
val user = User("Sang" , "Ho")
val admin: Admin = User("Sang" , "Ho")

assert(getFullName(user) == getAdminName(admin))