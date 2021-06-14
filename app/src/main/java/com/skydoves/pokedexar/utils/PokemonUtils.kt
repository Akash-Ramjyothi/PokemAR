/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.pokedexar.utils

import com.skydoves.pokedexar.R

object PokemonUtils {

  fun getTypeColor(type: String): Int {
    return when (type) {
      "fighting" -> R.color.fighting
      "flying" -> R.color.flying
      "poison" -> R.color.poison
      "ground" -> R.color.ground
      "rock" -> R.color.rock
      "bug" -> R.color.bug
      "ghost" -> R.color.ghost
      "steel" -> R.color.steel
      "fire" -> R.color.fire
      "water" -> R.color.water
      "grass" -> R.color.grass
      "electric" -> R.color.electric
      "psychic" -> R.color.psychic
      "ice" -> R.color.ice
      "dragon" -> R.color.dragon
      "fairy" -> R.color.fairy
      "dark" -> R.color.dark
      else -> R.color.gray_21
    }
  }

  fun getPokemonId(name: String): Int {
    return when (name) {
      "eevee" -> 133
      "abra" -> 63
      "bulbasaur" -> 1
      "charmander" -> 4
      "dratini" -> 147
      "jigglypuff" -> 39
      "magikarp" -> 129
      "mew" -> 151
      "oddish" -> 43
      "pikachu" -> 25
      "poliwag" -> 60
      "snorlax" -> 143
      "squirtle" -> 7
      else -> 0
    }
  }
}
