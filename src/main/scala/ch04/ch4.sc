
case class Producer(name: String, cost: Int, production: Int = 0) {
  var province: Province = _

  def setProvince(arg: Province): Unit = {
    province = arg
  }
}

class Province(
                val name: String,
                _producers: List[Producer],
                var totalProduction: Int = 0,
                var demand: Int,
                var price: Int,
              ) {
    var producers: List[Producer] = _producers.map(p => {
      p.setProvince(this)
      p
    })
}

def sampleProvinceData: Province = {
  val producers = List(
    Producer("Byzantium", 10, 9),
    Producer("Attalia", 12, 10),
    Producer("Sinope", 10, 6),
  )
  Province("Asia", producers, 30, 21, 10)
}