function fixDateMan(man) {
	for (var m of man) {
		m.datumVremeOdrzavanja= new Date(parseInt(m.datumVremeOdrzavanja));
	}
	return man;
}

Vue.component("man-view-user", {
	data: function () {
		return {
			manifestacije: [],
			manifestacijeZaPrikaz: [],
			manifestacijeProdavca:[],
			prikazRasprodatih: true,
			tipZaPrikaz: "Svi",
			tipovi: ["Svi", "KONCERT", "FESTIVAL", "PREDSTAVA", "SPORT", "OSTALO"],
			tipKarata: ["REGULAR","FAN_PIT","VIP"],
			tipKarte:"REGULAR",
			redSortiranjaOpcije:["Bez reda","Opadajuće","Rastuće"],
			redSortiranja:"Bez reda",
			sortirajPoOpcije:["Naziv","Datum","Cena","Lokacija"],
			sortirajPo:"Naziv",
			pretrazeno: false,
			pretragaNaziv:"",
			pretragaGrad:"",
			pretragaDrzava:"",
			komentari:[],
			pretragaCenaOd:0,
			pretragaCenaDo:10000,
			pretragaDatumOd: Date.now(),
			pretragaDatumDo: Date.now(),
			brojKarata: 0,
			ukupnaCena: 0,
			editVreme:"",
			editDatum:"",
			textKomentar:"",
			ocenaKomentar:0.0,
			kupacTip:"",
			novaManifestacija:{
				tip: "Koncert"
			},
			userRole: "",
			username:"",
		}
	},
	props:{
	},
	template: `
    <div>

      <h1 style="text-align:center;margin:30px"> Manifestacije: </h1>

	  

	  <div class="container marketing">

		<div class="row">
		<div class="col-xs-2">
		<label class="checkbox-inline">
			<input type="checkbox" id="RasprodateCheckbox" v-model="prikazRasprodatih" @change="pripremi()"> Prikaz rasprodatih
		</label>
	  	</div>
		  <div class="col-xs-3">
		  <div class="row">
		  <label>Tip manifestacije:</label>
		  <select v-model="tipZaPrikaz" @change="pripremi()">
		  <option v-for="tip in tipovi" v-bind:value="tip">
			  {{ tip }}
		  </option>
		</select>
	  	</div>
		  </div>


		</div>
			
		<div class="row">
			<div class="col-xs-2">
			<div class="row">
			<label>Sortiraj po:</label>
			<select v-model="sortirajPo" @change="pripremi()">
			<option v-for="opcija in sortirajPoOpcije" v-bind:value="opcija">
				{{ opcija }}
			</option>
			</select>
			</div>
			</div>

			<div class="col-xs-3">
			<div class="row">
			<label>Red sortiranja:</label>
			<select v-model="redSortiranja" @change="pripremi()">
			<option v-for="opcija in redSortiranjaOpcije" v-bind:value="opcija">
				{{ opcija }}
			</option>
			</select>
			</div>
			</div>
		</div>

		<div class="accordion" id="accordionExample">
			<div class="card">
				<div class="card-header" id="headingOne">
				<h5 class="mb-0">
					<button class="btn " type="button" data-toggle="collapse" @click="dropdownPretraga()" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
					Pretraga <span id="pretragaIcon" class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
					</button>
					<button class="btn " v-if="pretrazeno" type="button" @click="reset()">
					Reset <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
					</button>
				</h5>
			</div>

			<div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
				<div class="card-body">
					<label>Naziv:</label>
					<input type="text" v-model="pretragaNaziv" /> 
					<label>Grad:</label>
					<input type="text" v-model="pretragaGrad" /> 
					<label>Država:</label>
					<input type="text" v-model="pretragaDrzava" /> 
					<br />
					<label>Cena</label>
					<label>od:</label>
					<input type="number" v-model="pretragaCenaOd" min="0" max="10000"/> 
					<label>do:</label>
					<input type="number" v-model="pretragaCenaDo" min="0" max="10000"/> 
					<br />
					<label>Datum</label>
					<label>od:</label>
					<vuejs-datepicker  v-model="pretragaDatumOd" format="dd.MM.yyyy" ></vuejs-datepicker> 
					<label>do:</label>
					<vuejs-datepicker  v-model="pretragaDatumDo" format="dd.MM.yyyy" ></vuejs-datepicker>
					<br />
					<button class="btn " type="button" @click="trazi()" >
						Traži <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					</button>
				</div>
				</div>
			</div>
		</div>

      <div class="row-cols-3 justify-content-center">
        <div class="col-lg-3"  v-for="m in manifestacijeZaPrikaz" style="margin:20px">
            <img class="img-circle"  :src="'images/'+m.slikaPath" alt="Generic placeholder image" width="140" height="140">
            <h2>{{m.naziv}}<span v-if="userRole==='PRODAVAC' && m.prodavacUsername===username" class="badge">MM</span></h2>
            <h3>{{m.tip}}</h3>
            <p>Lokacija: {{m.grad}}, {{m.drzava}}</p>
            <p >Datum: {{m.datumVremeOdrzavanja | dateFormat('HH:mm DD.MM.YYYY')}}</p>
            <p>Cena: {{m.cenaRegular}}RSD </p>
			<p><div style="margin:auto;"><star-rating style="justify:center;" v-model="m.ocena" v-if="manProsla(m)" :increment="0.5" :read-only="true" :round-start-rating="false" :star-size="25"></star-rating></div></p>
			<button @click="ucitajKomentare(m)" type="button" class="btn btn-primary" data-toggle="modal" :data-target="'#info'+m.id">
			Prikaži detalje &raquo;
			</button>

            <button @click="pripremiModal(m)" v-if="userRole==='KUPAC' && m.slobodnaMesta!=0 && m.aktivna && !manProsla(m)" type="button" style="margin-top:10px" class="btn btn-primary" data-toggle="modal" :data-target="'#karteModal'+m.id">
			Rezerviši karte &raquo;
			</button>

			<button v-if="userRole==='PRODAVAC' && m.prodavacUsername===username" @click="pripremiEditModal(m)" type="button" style="margin-top:10px" class="btn btn-primary" data-toggle="modal" :data-target="'#editModal'+m.id">
			Izmeni informacije &raquo;
			</button>

			<br/>

			<button v-if="userRole==='PRODAVAC' && m.prodavacUsername===username" @click="obrisiMan(m)" type="button" style="margin-top:10px" class="btn btn-danger" data-toggle="modal" :data-target="'#editModal'+m.id">
			Obriši &raquo;
			</button>

			<button @click="aktiviraj(m)" v-if="userRole==='ADMINISTRATOR' && !m.aktivna" type="button" style="margin-bottom:10px" class="btn btn-success" >
			Aktiviraj <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
			</button>

			<!-- Modal -->
				<div class="modal fade" :id="'info'+m.id" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-scrollable" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h2 class="modal-title" >Detalji manifestacije {{m.naziv}}</h2>
						<h3 class="modal-title" >{{m.tip}}</h3>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
						<div class="row">
							<div class="col-md-4" >
								<img class="img-thumbnail" :src="'images/'+m.slikaPath" alt="Generic placeholder image" width="140" height="140">
							</div>
							<div class="col-md-4 ml-auto" >
								<p>Broj mesta: {{m.brojMesta}}</p>
								<p>Preostali broj karata: {{m.slobodnaMesta}}</p>
								<p >Datum: {{m.datumVremeOdrzavanja | dateFormat('HH:mm DD.MM.YYYY')}}</p>
								<p>Cena: {{m.cenaRegular}}RSD </p>
								<p v-if="!m.aktivna">Status: Neaktivno <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></p>
								<p v-if="m.aktivna">Status: Aktivno <span class="glyphicon glyphicon-ok" aria-hidden="true"></span></p>
								<p>Lokacija: {{m.grad}}, {{m.drzava}}</p>
								<p><div style="margin:auto;"><star-rating style="justify:center;" v-model="m.ocena" v-if="manProsla(m)" :increment="0.5" :read-only="true" :round-start-rating="false" :star-size="25"></star-rating></div></p>
								
							</div>

							
							
						</div>
						</div>

						<google-map :center="{lat: m.geoSirina, lng: m.geoDuzina}" :zoom="16" style="width: 100%; height: 300px">
							<google-marker :position="{lat: m.geoSirina, lng: m.geoDuzina}" ></google-marker>
						</google-map>

						<br/>
						<br/>

						<h3>Komentari:</h3>

						<form :id="'formKomentar'+m.id" v-if="userRole==='KUPAC'">
							<textarea v-model="textKomentar" id="'textA'+m.id" rows="4" cols="50" required>
							</textarea>
							<br/>
							<p><div style="margin:auto;"><star-rating style="justify:center;" v-model="ocenaKomentar" :increment="0.5" :round-start-rating="false" :star-size="25"></star-rating></div></p>
							<br/>
							<button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="komentarisi(m)">Komentariši</button>
						</form>

						<div v-for="komentar in komentari">
							<div v-if="!((userRole==='PRODAVAC' && m.prodavacUsername!==username) && (komentar.status==='ODBIJEN' || komentar.status==='KREIRAN'))">
								<p>{{komentar.kupacUsername}} 
								<div v-if="((userRole==='PRODAVAC' && m.prodavacUsername===username) || userRole==='ADMINISTRATOR') && komentar.status==='ODBIJEN'">ODBIJEN</div>
								<div v-if="((userRole==='PRODAVAC' && m.prodavacUsername===username)  || userRole==='ADMINISTRATOR') && komentar.status==='PRIHVACEN'">PRIHVAĆEN</div>
								<div v-if="((userRole==='PRODAVAC' && m.prodavacUsername===username)  || userRole==='ADMINISTRATOR') && komentar.status==='KREIRAN'">KREIRAN</div>
								</p>
								<textarea v-model="komentar.tekst" rows="4" cols="50" readonly>
								</textarea>
								<p><div style="margin:auto;"><star-rating style="justify:center;" v-model="komentar.ocena" :increment="0.5" :read-only="true" :round-start-rating="false" :star-size="25"></star-rating></div></p>
								<br/>

								<div v-if="(userRole==='PRODAVAC' && m.prodavacUsername===username)  && komentar.status==='KREIRAN'">
									<div class="row">

										<button @click="prihvatiKomentar(komentar)" type="button" style="margin-left:10px" class="btn btn-success">Prihavti</button>
										<button @click="odbijKomentar(komentar)" type="button" style="margin-left:10px" class="btn btn-danger">Odbij</button>

									</div>
								</div>

								<br/>
							</div>

						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					</div>
					</div>
				</div>
				</div>

				<!-- Modal -->
				<div class="modal fade" :id="'editModal'+m.id" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-scrollable" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h2 class="modal-title" >Izmena informacija manifestacije {{m.naziv}}</h2>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
					<div class="container-fluid">
					<form class="form-signin" data-toggle="validator" :id="'formEdit'+m.id" role="form">
						<div class="form-group">
						<label :for="'inputNaziv'+m.id" class="control-label">Naziv manifestacije</label>
						<input v-model="m.naziv" type="text" class="form-control" :id="'inputNaziv'+m.id" data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
						<label :for="'inputGrad'+m.id" class="control-label">Grad</label>
						<input type="text" v-model="m.grad" class="form-control" :id="'inputGrad'+m.id" data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputDrzava'+m.id" class="control-label">Država</label>
							<input type="text" v-model="m.drzava" class="form-control" :id="'inputDrzava'+m.id"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputUlica'+m.id" class="control-label">Ulica</label>
							<input type="text" v-model="m.ulica"  class="form-control" :id="'inputUlica'+m.id"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputKBroj'+m.id" class="control-label">Kućni broj</label>
							<input type="text" v-model="m.kucniBroj" class="form-control" :id="'inputKBroj'+m.id"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputPBroj'+m.id" class="control-label">Poštanski broj</label>
							<input type="text" v-model="m.postanskiBroj"  class="form-control" :id="'inputPBroj'+m.id"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group" required>
									<label>Tip manifestacije:</label>
									<select v-model="m.tip">
									<option v-for="tip in tipovi" v-bind:value="tip">
										{{ tip }}
									</option>
									</select>
						</div>

						<div class="form-group">
							<label :for="'datePicker'+m.id">Datum:</label>
							<input type="date" :id="'datePicker'+m.id" v-model="editDatum"
								min="1900-01-01" data-error="Polje ne sme biti prazno" required>
								<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label for="'timePicker'+m.id">Vreme:</label>
							<input type="time" :id="'timePicker'+m.id" v-model="editVreme"
								min="1900-01-01" data-error="Polje ne sme biti prazno" required>
								<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputCena'+m.id" class="control-label">Cena karte</label>
							<input type="number" v-model="m.cenaRegular" min="100" max="10000" maxlength="15" class="form-control" :id="'inputCena'+m.id"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputMesta'+m.id" class="control-label">Broj mesta</label>
							<input type="number" min="10" v-model="m.brojMesta" max="50000" maxlength="15" class="form-control" :id="'inputMesta'+m.id"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label :for="'inputSlika'+m.id" class="control-label">Slika</label>
							<input type="file" accept="image/*" @change="uploadImage($event,m)" class="form-control" id="'inputSlika'+m.id">
							<div class="help-block with-errors"></div>
						</div>


						<button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="submitIzmene(m)">Sačuvaj izmene</button>
					</form>
				</div>
				</div>
				</div>
				</div>
				</div>



                <!-- Modal -->
				<div class="modal fade" :id="'karteModal'+m.id" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-scrollable" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h2 class="modal-title" >Kupovina karata za manifestaciju {{m.naziv}}</h2>
						<h3 class="modal-title" >{{m.tip}}</h3>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
						<div class="row">
							<div class="col-md-4" >
								<img class="img-thumbnail" :src="'images/'+m.slikaPath" alt="Generic placeholder image" width="140" height="140">
							</div>
							<div class="col-md-4 ml-auto" >
                                <p>Broj mesta: {{m.brojMesta}}</p>
                                <p>Preostali broj karata: {{m.slobodnaMesta}}</p>
                                <p >Datum: {{m.datumVremeOdrzavanja | dateFormat('HH:mm DD.MM.YYYY')}}</p>
                                <p>Lokacija: {{m.grad}}, {{m.drzava}}</p>

                                
							</div>

                            <h3>Cenovnik:</h3>

                                <p>Regular karta: {{m.cenaRegular}}RSD</p>
                                <p>Fan pit: {{m.cenaRegular * 2}}RSD</p>
                                <p>VIP: {{m.cenaRegular * 4}}RSD</p>

                                <div class="col-md-4 ml-auto" >
                                    <label for="brojKarataInput" class="control-label">Broj karata:</label>
									<input type="range" class="form-range" min="0" :max=m.slobodnaMesta step="1" id="brojKarataInput" v-model="brojKarata" @change="racunajCenu(m)" >
									<input type="number" :value=brojKarata id="rangePrimary" disabled/>
									<label>Tip karti:</label>
									<select v-model="tipKarte" @change="racunajCenu(m)">
									<option v-for="tip in tipKarata" v-bind:value="tip">
										{{ tip }}
									</option>
									</select>
									<label>Ukupna cena:</label>
									<input type="number" :value=ukupnaCena disabled/>
									<br/>
									<button @click="kupiKarte(m)" type="button" class="btn btn-success" >Kupi</button>
                                </div>

							
						</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					</div>
					</div>
				</div>
				</div>
        </div><!-- /.col-lg-4 -->

		<div class="col-lg-3" style="margin:20px" v-if="userRole==='PRODAVAC'">
			<img class="img-circle" @click="openModal()" src="images/plus.png" alt="Generic placeholder image" width="140" height="140" >
			<h2 style="text-align:center">Dodaj manifestaciju</h2>

			<!-- Modal -->
				<div class="modal fade" id="modal1" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-scrollable" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h2 class="modal-title" >Nova manifestacija</h2>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<form class="form-signin" data-toggle="validator" id="formNew" role="form">
								<div class="form-group">
								<label for="inputNazivAdd" class="control-label">Naziv manifestacije</label>
								<input v-model="novaManifestacija.naziv" type="text" class="form-control" id="inputNazivAdd" data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
								<label for="inputGradAdd" class="control-label">Grad</label>
								<input type="text" v-model="novaManifestacija.grad" class="form-control" id="inputGradAdd" data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputDrzavaAdd" class="control-label">Država</label>
									<input type="text" v-model="novaManifestacija.drzava"   class="form-control" id="inputDrzavaAdd"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputUlicaAdd" class="control-label">Ulica</label>
									<input type="text" v-model="novaManifestacija.ulica"  class="form-control" id="inputUlicaAdd"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputKBrojAdd" class="control-label">Kućni broj</label>
									<input type="text" v-model="novaManifestacija.kucniBroj" class="form-control" id="inputKBrojAdd"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputPBrojAdd" class="control-label">Poštanski broj</label>
									<input type="text" v-model="novaManifestacija.postanskiBroj"  class="form-control" id="inputPBrojAdd"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group" required>
									<label>Tip manifestacije:</label>
									<select v-model="novaManifestacija.tip">
									<option v-for="tip in tipovi" v-bind:value="tip">
										{{ tip }}
									</option>
									</select>
								</div>

								<div class="form-group">
									<label for="datePickeAddr">Datum:</label>
									<input type="date" id="datePickerAdd" v-model="novaManifestacija.datumVremeOdrzavanja"
										min="1900-01-01" data-error="Polje ne sme biti prazno" required>
										<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="timePickeAddr">Vreme:</label>
									<input type="time" id="timePickerAdd" v-model="novaManifestacija.vreme"
										min="1900-01-01" data-error="Polje ne sme biti prazno" required>
										<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputCenaAdd" class="control-label">Cena karte</label>
									<input type="number" v-model="novaManifestacija.cenaRegular" min="100" max="10000" maxlength="15" class="form-control" id="inputCenaAdd"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputMestaAdd" class="control-label">Broj mesta</label>
									<input type="number" min="10" v-model="novaManifestacija.brojMesta" max="50000" maxlength="15" class="form-control" id="inputMestaAdd"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="submitMan()">Dodaj manifestaciju</button>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					</div>
					</div>
				</div>
				</div>

		</div>
      </div><!-- /.row -->
		
  
	  </div><!-- /.container -->
	  </div>

      
    	  
`
	,
	methods: {
		async prihvatiKomentar(k){
			k.status="PRIHVACEN"
			await axios.post(`komentari/prihvati/`+k.id,{data:{}, headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })
		},
		async odbijKomentar(k){
			k.status="ODBIJEN"
			await axios.post(`komentari/odbij/`+k.id,{ data:{},headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })
		},
		pripremiEditModal(m){
			var parsed = moment(m.datumVremeOdrzavanja);
			this.editDatum= parsed.format('YYYY-MM-DD');
			this.editVreme=parsed.format('HH:mm');
			console.log(this.editDatum)
		},
		async ucitajKomentare(m){
			this.komentari=[]
			if(this.userRole==="ADMINISTRATOR" || this.userRole==="PRODAVAC"){
				await axios.get(`/komentari/manifestacija/svi/`+m.id,{data:{}, headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
					const kom=[]
					response.data.forEach(element=>{
						kom.push({
							id:element.id,
							kupacUsername: element.kupacUsername,
							manifestacijaId:element.manifestacijaId,
							tekst:element.tekst,
							ocena:element.ocena,
							status:element.status,
						})
					})
					this.komentari=kom
				})
			}else{
				await axios.get(`/komentari/manifestacija/prihvaceni/`+m.id,{ data:{},headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
					const kom=[]
					response.data.forEach(element=>{
						kom.push({
							id:element.id,
							kupacUsername: element.kupacUsername,
							manifestacijaId:element.manifestacijaId,
							tekst:element.tekst,
							ocena:element.ocena,
							status:element.status,
						})
					})
					this.komentari=kom
				})
			}
		},
		manProsla(m){
			return m.datumVremeOdrzavanja<Date.now()
		},
		openModal(){
			$('#modal1').modal();
		},
		uploadImage(event,m) {
		
			let data = new FormData();
			data.append('name', m.naziv+"_slika");
			data.append('file', event.target.files[0]); 
		
			let config = {
			  header : {
				'Content-Type' : 'image/png'
			  }
			}
		
			axios.put(
			  `manifestacije/slika/`+m.id, 
			  data,
			  config,{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }
			).then(
			  response => {
				console.log('image upload response > ', response)
			  }
			)
		  },
		async submitIzmene(m){
			//TO DO poslati izmene na backend
			if ( $('#formEdit'+m.id)[0].checkValidity() ) {
				m.datumVremeOdrzavanja=new Date(this.editDatum + " " + this.editVreme).getTime()
				$('#formEdit'+m.id).submit(function (evt) {
					evt.preventDefault();
					
					
				});

				await axios.get("https://maps.google.com/maps/api/geocode/json?address=" + m.drzava+" "+m.grad+" "+m.ulica+ " "+ m.kucniBroj+" "+m.postanskiBroj + "&sensor=false&key=AIzaSyDs1wPwu4XZNfH5ttEj7md96W0nWJn0RGU").then(response=>{
					console.log(response.data)	
					m.geoDuzina=response.data.results[0].geometry.location.lng
					m.geoSirina=response.data.results[0].geometry.location.lat
				})

				console.log(m)

				await axios.put(`/manifestacije`,m,{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
					alert("Izmene uspešno sačuvane!")
				}).catch(err=>{
					alert("Došlo je do greške!")
				})
					
				m.datumVremeOdrzavanja=new Date(parseInt(new Date(this.editDatum + " " + this.editVreme).getTime()))
				this.pripremi()
			}
		},
		async obrisiMan(m){
			await axios.delete(`manifestacije/`+m.id,{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })
            this.manifestacije=this.manifestacije.filter(man=>man.id!=m.id)
            this.manifestacijeZaPrikaz=this.manifestacijeZaPrikaz.filter(man=>man.id!=m.id)
		},
		async komentarisi(m){
			if ( $('#formKomentar'+m.id)[0].checkValidity() ) {
				$('#formKomentar'+m.id).submit(function (evt) {
					evt.preventDefault();
				});
				let noviKomentar={
					tekst:this.textKomentar,
					ocena:this.ocenaKomentar,
					manifestacijaId:m.id,
					kupacUsername:this.username
				}

				

				await axios.post(`/komentari`,noviKomentar,{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })

				this.textKomentar=""
				this.ocenaKomentar=0
			}
		},
		async submitMan(){
			//TO DO poslati novu manifestaciju na backend
			if ( $('#formNew')[0].checkValidity() ) {
				$('#formNew').submit(function (evt) {
					evt.preventDefault();
				});
				this.novaManifestacija.id="aaaa4545"
				this.novaManifestacija.slobodnaMesta=this.novaManifestacija.brojMesta
				this.novaManifestacija.aktivna=false
				this.novaManifestacija.prodavacUsername=this.username
				this.novaManifestacija.slikaPath=""
				this.novaManifestacija.datumVremeOdrzavanja=new Date(parseInt(new Date(this.novaManifestacija.datumVremeOdrzavanja + " " + this.novaManifestacija.vreme).getTime()))
				delete this.novaManifestacija.vreme
				console.log(this.novaManifestacija)
				this.manifestacije.push(this.novaManifestacija)

				this.novaManifestacija.datumVremeOdrzavanja=this.novaManifestacija.datumVremeOdrzavanja.getTime()
				this.novaManifestacija.cenaRegular=parseInt(this.novaManifestacija.cenaRegular)
				this.novaManifestacija.brojMesta=parseInt(this.novaManifestacija.brojMesta)
				this.novaManifestacija.slobodnaMesta=this.novaManifestacija.brojMesta
				console.log(this.novaManifestacija.datumVremeOdrzavanja)

				await axios.get("http://maps.google.com/maps/api/geocode/json?address=" + this.novaManifestacija.drzava+" "+this.novaManifestacija.grad+" "+this.novaManifestacija.ulica+ " "+ this.novaManifestacija.kucniBroj+" "+this.novaManifestacija.postanskiBroj + "&sensor=false").then(response=>{
					console.log(response.data)
				})

				await axios.post(`/manifestacije`,this.novaManifestacija,{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
					alert("Uspešno kreiranan manifestacija!")
				}).catch(err=>{
					alert("Došlo je do greške!")
				})

				this.novaManifestacija={tip:"Koncert"}
				this.pripremi()
			}
		},
		pripremi(){

            this.manifestacijeZaPrikaz = [...this.manifestacije]

            if(this.pretrazeno){
                this.trazi()
            }
            this.filtriraj()
            this.sortiraj()
        },
		filtriraj() {
			if(!this.prikazRasprodatih){
				this.manifestacijeZaPrikaz=this.manifestacijeZaPrikaz.filter(m=>m.slobodnaMesta>0)
			}
			if (this.tipZaPrikaz !== "Svi") {
				this.manifestacijeZaPrikaz = this.manifestacijeZaPrikaz.filter(m => m.tip === this.tipZaPrikaz)
			}
		},
		porediManifestacije(m1,m2){
			let m1Fix=""
			let m2Fix=""
			switch(this.sortirajPo) {
				case "Naziv":
				  m1Fix=m1.naziv.toUpperCase()
				  m2Fix=m2.naziv.toUpperCase()
				  break;
				case "Cena":
					m1Fix=m1.cenaRegular
					m2Fix=m2.cenaRegular
				  break;
				case "Lokacija":
				  m1Fix=m1Fix.concat(m1.grad.toUpperCase(),m1.drzava.toUpperCase())
				  m2Fix=m2Fix.concat(m2.grad.toUpperCase(),m2.drzava.toUpperCase())
				  break;
				case "Datum":
					m1Fix=m1.datumVremeOdrzavanja
					m2Fix=m2.datumVremeOdrzavanja
				  break;
				default:	  
			}
			let comparison = 0;
			if (m1Fix > m2Fix) {
			  comparison = 1;
			} else if (m1Fix < m2Fix) {
			  comparison = -1;
			}

			if(this.redSortiranja=="Opadajuće"){
				return comparison* -1;
			}else{
				return comparison;
			}
		},
		porediManifestacijePocetak(m1,m2){
			let m1Fix=m1.datumVremeOdrzavanja
			let m2Fix=m2.datumVremeOdrzavanja
			let now = Date.now();
			let comparison = 0;
			if (Math.abs(m1Fix-now) > Math.abs(m2Fix-now)) {
			  comparison = 1;
			} else if (Math.abs(m1Fix-now) < Math.abs(m2Fix-now)) {
			  comparison = -1;
			}

			return comparison
		},
		sortiraj(){
			if(this.redSortiranja!=="Bez reda"){
				this.manifestacijeZaPrikaz.sort(this.porediManifestacije)
			}
		},
		trazi(){
			if(!this.pretrazeno){
				this.pretrazeno=true
				this.prikazRasprodatih=true
				this.tipZaPrikaz="Svi"
				this.sortirajPo="Naziv"
				this.redSortiranja="Bez reda"
			}
			this.manifestacijeZaPrikaz =this.manifestacije.filter(m=>m.naziv.toUpperCase().includes(this.pretragaNaziv.toUpperCase()) && m.grad.toUpperCase().includes(this.pretragaGrad.toUpperCase()) && m.lokacija.drzava.toUpperCase().includes(this.pretragaDrzava.toUpperCase()))
			this.manifestacijeZaPrikaz =this.manifestacijeZaPrikaz.filter(m=>m.cenaRegular<=this.pretragaCenaDo && m.cenaRegular>=this.pretragaCenaOd)
			this.manifestacijeZaPrikaz =this.manifestacijeZaPrikaz.filter(m=>m.datumVremeOdrzavanja<=this.pretragaDatumDo && m.datumVremeOdrzavanja>=this.pretragaDatumOd)
		},
		reset(){
			this.pretrazeno=false
			this.prikazRasprodatih=true
			this.tipZaPrikaz="Svi"
			this.sortirajPo="Naziv"
			this.redSortiranja="Bez reda"
			this.pretragaNaziv=""
			this.pretragaGrad=""
			this.pretragaDrzava=""
			this.pretragaCenaDo=10000
			this.pretragaCenaOd=0
			this.pretragaDatumDo=Date.now()
			this.pretragaDatumOd=Date.now()
			this.manifestacijeZaPrikaz= [...this.manifestacije]
		},
		dropdownPretraga(){
			$("#pretragaIcon").toggleClass("glyphicon-arrow-down");
			$("#pretragaIcon").toggleClass("glyphicon-arrow-up");
		},
		pripremiModal(m){
			this.tipKarte="REGULAR"
			this.brojKarata=0
			this.racunajCenu(m)
		},
		async aktiviraj(m){
			m.aktivna=true
			await axios.post(`manifestacije/aktiviraj/`+m.id,{data:{}},{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })
			this.pripremi()
		},
		racunajCenu(m){
			switch(this.tipKarte){
				case "REGULAR":
					this.ukupnaCena=m.cenaRegular*this.brojKarata
					break
				case "FAN_PIT":
					this.ukupnaCena=m.cenaRegular*this.brojKarata*2
					break
				case "VIP":
					this.ukupnaCena=m.cenaRegular*this.brojKarata*4
					break
				default:

			}

			switch(this.kupacTip){
				case "BRONZANI":
					this.ukupnaCena=this.ukupnaCena*0.97
					break
				case "SREBRNI":
					this.ukupnaCena=this.ukupnaCena*0.95
					break
				case "ZLATNI":
					this.ukupnaCena=this.ukupnaCena*0.9
					break
				default:
			}
		},
		async kupiKarte(m){
			if(this.brojKarata==0){
				alert("Nije moguće kupiti 0 karata")
			}else{
				await axios.post(`karte/rezervisi`,{
					manifestacijaId: m.id,
					nazivManifestacije:m.naziv,
					datumVremeOdrzavanja:m.datumVremeOdrzavanja.getTime(),
					cena: this.ukupnaCena,
					kupacUsername:this.username,
					tip: this.tipKarte,
					brojKarata: this.brojKarata,
				},{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })

				m.slobodnaMesta-=this.brojKarata
				this.pripremiModal(m)
				alert("KUPLJENO")
			}

		},
	},
	async mounted() {
		$(document).ready(function () {
			var options = {
				max_value: 6,
				step_size: 0.5,
				selected_symbol_type: 'hearts',
				initial_value: 3,
			}
			$(".rate").rate();
		});

		//TO DO: izvrsiti ucitavnja na osnovu user role 

		this.userRole=window.localStorage.getItem('uloga')
		this.username=window.localStorage.getItem('username')

		if(this.userRole==="KUPAC"){
			await axios.get(`kupci/`+window.localStorage.getItem('username'),{data:{}, headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
				this.kupacTip=response.data.tip
			})
		}
		
		await axios.get(`/manifestacije`,{data:{}, headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                     const man=[]
                     
                     response.data.forEach(element => {
                    	 console.log(element)
                         man.push({
                        	id:element.id,
                 			naziv: element.naziv,
							grad: element.lokacija.grad,
                 			drzava: element.lokacija.drzava,
							geoDuzina: element.lokacija.geoDuzina,
							geoSirina: element.lokacija.geoSirina,
							ulica: element.lokacija.ulica,
							kucniBroj: element.lokacija.kucniBroj,
							postanskiBroj: element.lokacija.postanskiBroj,
                 			slikaPath: element.slikaPath,
                 			tip: element.tip,
                 			datumVremeOdrzavanja: element.datumVremeOdrzavanja,
                 			cenaRegular: element.cenaRegular,
                 			ocena: element.ocena,
                 			brojMesta: element.brojMesta,
                 			slobodnaMesta: element.slobodnaMesta,
                 			aktivna: element.aktivna,
							prodavacUsername: element.prodavacUsername,
                         })
                         
                     });
                     this.manifestacije=man
					 fixDateMan(this.manifestacije)
                     this.manifestacije=this.manifestacije.sort(this.porediManifestacijePocetak)
             		this.manifestacijeZaPrikaz = [...this.manifestacije]
             	
                 })

	},
	filters: {
		dateFormat: function (value, format) {
			var parsed = moment(value);
			return parsed.format(format);
		}
	},
	components:{
		vuejsDatepicker,
	}
});