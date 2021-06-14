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

package com.skydoves.pokedexar.repository

import androidx.annotation.WorkerThread
import com.skydoves.pokedexar.mapper.ErrorResponseMapper
import com.skydoves.pokedexar.model.PokemonErrorResponse
import com.skydoves.pokedexar.model.PokemonInfo
import com.skydoves.pokedexar.network.PokedexClient
import com.skydoves.pokedexar.persistence.PokemonInfoDao
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class DetailRepository @Inject constructor(
  private val pokedexClient: PokedexClient,
  private val pokemonInfoDao: PokemonInfoDao
) : Repository {

  @WorkerThread
  fun fetchPokemonInfo(
    name: String,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow<PokemonInfo?> {
    val pokemonInfo = pokemonInfoDao.getPokemonInfo(name)
    if (pokemonInfo == null) {
      /**
       * fetch [PokemonInfo] from the network and getting [ApiResponse] asynchronously.
       * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#suspendonsuccess-suspendonerror-suspendonexception)
       * */
      val response = pokedexClient.fetchPokemonInfo(name = name)
      response.suspendOnSuccess {
        data.whatIfNotNull { response ->
          pokemonInfoDao.insertPokemonInfo(response)
          emit(response)
        }
      }
        // handle the case when the API request gets an error response.
        // e.g. internal server error.
        .onError(ErrorResponseMapper) {
          /** maps the [ApiResponse.Failure.Error] to the [PokemonErrorResponse] using the mapper. */
          onError("[Code: $code]: $message")
        }
        // handle the case when the API request gets an exception response.
        // e.g. network connection error.
        .onException { onError(message) }
    } else {
      emit(pokemonInfo)
    }
  }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}
