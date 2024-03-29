package predatorprey

object WorldConfiguration {
    val worldWidth : Int = 800
	val worldHeight : Int = 800
	val initialHares : Int = 100
	val initialLynx : Int = 100
	val hareBirthRate : Long = 30000 //30 seconds
	val maxHareAge : Long = 300000 // 300 seconds
        val initialLynxEnergy = 10
	val lynxEnergyToReproduce = 10
	val energyPerEatenHare = 40
        val lynxBirthRate : Long = 60000 //60 seconds
	val maxLynxAge : Long = 600000 //600 seconds
	val defaultEnergy = 10
    
}
