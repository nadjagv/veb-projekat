function fixDate(man) {
	for (var m of man) {
		m.datumPocetka = new Date(parseInt(m.datumPocetka));
	}
	return man;
}

Vue.component("man-view-user", {
	data: function () {
		return {
			manifestacije: [],
			manifestacijeZaPrikaz: [],
			prikazRasprodatih: true,
			tipZaPrikaz: "Svi",
			tipovi: ["Svi", "Sport", "Koncert", "Predstava", "Ostalo","Festival"],
			tipKarata: ["Regular","Fan Pit","VIP"],
			tipKarte:"Regular",
			redSortiranjaOpcije:["Bez reda","Opadajuće","Rastuće"],
			redSortiranja:"Bez reda",
			sortirajPoOpcije:["Naziv","Datum","Cena","Lokacija"],
			sortirajPo:"Naziv",
			pretrazeno: false,
			pretragaNaziv:"",
			pretragaGrad:"",
			pretragaDrzava:"",
			pretragaCenaOd:0,
			pretragaCenaDo:10000,
			pretragaDatumOd: Date.now(),
			pretragaDatumDo: Date.now(),
			brojKarata: 0,
			ukupnaCena: 0,
			novaManifestacija:{
				tip: "Koncert"
			},
		}
	},
	props:{
		userRole: String,
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
        <div class="col-lg-3" v-for="m in manifestacijeZaPrikaz" style="margin:20px">
            <img class="img-circle" :src="'images/'+m.slika" alt="Generic placeholder image" width="140" height="140">
            <h2>{{m.naziv}}</h2>
            <h3>{{m.tip}}</h3>
            <p>Lokacija: {{m.grad}}, {{m.drzava}}</p>
            <p >Datum: {{m.datumPocetak | dateFormat('HH:mm DD.MM.YYYY')}}</p>
            <p>Cena: {{m.cena}}RSD </p>
			<p><div style="margin:auto;"><star-rating style="justify:center;" v-model="m.ocena" v-if="m.prosla" :increment="0.5" :read-only="true" :round-start-rating="false" :star-size="25"></star-rating></div></p>
			<button type="button" class="btn btn-primary" data-toggle="modal" :data-target="'#'+m.id">
			Prikaži detalje &raquo;
			</button>

            <button @click="pripremiModal(m)" v-if="userRole==='Kupac' && m.slobodnaMesta!=0 && m.aktivna && !m.prosla" type="button" style="margin-top:10px" class="btn btn-primary" data-toggle="modal" :data-target="'#karteModal'+m.id">
			Rezerviši karte &raquo;
			</button>

			<button v-if="userRole==='Prodavac'" type="button" style="margin-top:10px" class="btn btn-primary" data-toggle="modal" :data-target="'#editModal'+m.id">
			Izmeni informacije &raquo;
			</button>

			<!-- Modal -->
				<div class="modal fade" :id="m.id" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
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
								<img class="img-thumbnail" :src="'images/'+m.slika" alt="Generic placeholder image" width="140" height="140">
							</div>
							<div class="col-md-4 ml-auto" >
								<p>Broj mesta: {{m.brojMesta}}</p>
								<p>Preostali broj karata: {{m.slobodnaMesta}}</p>
								<p >Datum: {{m.datumPocetak | dateFormat('HH:mm DD.MM.YYYY')}}</p>
								<p>Cena: {{m.cena}}RSD </p>
								<p v-if="!m.aktivna">Status: Neaktivno <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></p>
								<p v-if="m.aktivna">Status: Aktivno <span class="glyphicon glyphicon-ok" aria-hidden="true"></span></p>
								<p>Lokacija: {{m.grad}}, {{m.drzava}}</p>
								<p><div style="margin:auto;"><star-rating style="justify:center;" v-model="m.ocena" v-if="m.prosla" :increment="0.5" :read-only="true" :round-start-rating="false" :star-size="25"></star-rating></div></p>
							</div>
							
						</div>
						</div>

						<br/>
						<br/>

						<h3>Komentari:</h3>

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
					<form class="form-signin" data-toggle="validator" id="formEdit" role="form">
						<div class="form-group">
						<label for="inputNaziv" class="control-label">Naziv manifestacije</label>
						<input v-model="m.naziv" type="text" class="form-control" id="inputNaziv" data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
						<label for="inputGrad" class="control-label">Grad</label>
						<input type="text" v-model="m.grad" class="form-control" id="inputGrad" data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label for="inputDrzava" class="control-label">Država</label>
							<input type="text" v-model="m.drzava" pattern="^[_A-z0-9]{1,}$" maxlength="15" class="form-control" id="inputDrzava"  data-error="Polje ne sme biti prazno" required>
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
							<label for="datePicker">Datum:</label>
							<vuejs-datepicker id="datePicker" v-model="m.datumPocetka" format="dd.MM.yyyy" data-error="Polje ne sme biti prazno" required></vuejs-datepicker>
								<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label for="inputCena" class="control-label">Cena karte</label>
							<input type="number" v-model="m.cena" min="100" max="10000" maxlength="15" class="form-control" id="inputCena"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>

						<div class="form-group">
							<label for="inputMesta" class="control-label">Broj mesta</label>
							<input type="number" min="10" v-model="m.brojMesta" max="50000" maxlength="15" class="form-control" id="inputMesta"  data-error="Polje ne sme biti prazno" required>
							<div class="help-block with-errors"></div>
						</div>


						<button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="submitIzmene()">Sačuvaj izmene</button>
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
								<img class="img-thumbnail" :src="'images/'+m.slika" alt="Generic placeholder image" width="140" height="140">
							</div>
							<div class="col-md-4 ml-auto" >
                                <p>Broj mesta: {{m.brojMesta}}</p>
                                <p>Preostali broj karata: {{m.slobodnaMesta}}</p>
                                <p >Datum: {{m.datumPocetak | dateFormat('HH:mm DD.MM.YYYY')}}</p>
                                <p>Lokacija: {{m.grad}}, {{m.drzava}}</p>

                                
							</div>

                            <h3>Cenovnik:</h3>

                                <p>Regular karta: {{m.cena}}RSD</p>
                                <p>Fan pit: {{m.cena * 2}}RSD</p>
                                <p>VIP: {{m.cena * 4}}RSD</p>

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

		<div class="col-lg-3" style="margin:20px">
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
								<label for="inputNaziv" class="control-label">Naziv manifestacije</label>
								<input v-model="novaManifestacija.naziv" type="text" class="form-control" id="inputNaziv" data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
								<label for="inputGrad" class="control-label">Grad</label>
								<input type="text" v-model="novaManifestacija.grad" class="form-control" id="inputGrad" data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputDrzava" class="control-label">Država</label>
									<input type="text" v-model="novaManifestacija.drzava" pattern="^[_A-z0-9]{1,}$" maxlength="15" class="form-control" id="inputDrzava"  data-error="Polje ne sme biti prazno" required>
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
									<label for="datePicker">Datum:</label>
									<input type="date" id="datePicker" v-model="novaManifestacija.datumPocetka"
										min="1900-01-01" data-error="Polje ne sme biti prazno" required>
										<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputCena" class="control-label">Cena karte</label>
									<input type="number" v-model="novaManifestacija.cena" min="100" max="10000" maxlength="15" class="form-control" id="inputCena"  data-error="Polje ne sme biti prazno" required>
									<div class="help-block with-errors"></div>
								</div>

								<div class="form-group">
									<label for="inputMesta" class="control-label">Broj mesta</label>
									<input type="number" min="10" v-model="novaManifestacija.brojMesta" max="50000" maxlength="15" class="form-control" id="inputMesta"  data-error="Polje ne sme biti prazno" required>
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
		openModal(){
			$('#modal1').modal();
		},
		submitIzmene(){
			//TO DO poslati izmene na backend
			if ( $('#formEdit')[0].checkValidity() ) {
				$('#formEdit').submit(function (evt) {
					evt.preventDefault();
					
					
				});
			}
		},
		submitMan(){
			//TO DO poslati novu manifestaciju na backend
			if ( $('#formNew')[0].checkValidity() ) {
				$('#formNew').submit(function (evt) {
					evt.preventDefault();
					
					
				});
				console.log(this.novaManifestacija)
				this.novaManifestacija.id=this.manifestacije.length+1
				this.novaManifestacija.slobodnaMesta=this.novaManifestacija.brojMesta
				this.novaManifestacija.aktivna=false
				this.novaManifestacija.datumPocetka=Date.parse(this.novaManifestacija.datumPocetka)
				console.log(this.novaManifestacija)
				this.manifestacije.push(this.novaManifestacija)
				fixDate(this.manifestacije)
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
					m1Fix=m1.cena
					m2Fix=m2.cena
				  break;
				case "Lokacija":
				  m1Fix=m1Fix.concat(m1.grad.toUpperCase(),m1.drzava.toUpperCase())
				  m2Fix=m2Fix.concat(m2.grad.toUpperCase(),m2.drzava.toUpperCase())
				  break;
				case "Datum":
					m1Fix=m1.datumPocetak
					m2Fix=m2.datumPocetak
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
			let m1Fix=m1.datumPocetak
			let m2Fix=m2.datumPocetak
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
			this.manifestacijeZaPrikaz =this.manifestacije.filter(m=>m.naziv.toUpperCase().includes(this.pretragaNaziv.toUpperCase()) && m.grad.toUpperCase().includes(this.pretragaGrad.toUpperCase()) && m.drzava.toUpperCase().includes(this.pretragaDrzava.toUpperCase()))
			this.manifestacijeZaPrikaz =this.manifestacijeZaPrikaz.filter(m=>m.cena<=this.pretragaCenaDo && m.cena>=this.pretragaCenaOd)
			this.manifestacijeZaPrikaz =this.manifestacijeZaPrikaz.filter(m=>m.datumPocetak<=this.pretragaDatumDo && m.datumPocetak>=this.pretragaDatumOd)
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
			this.tipKarte="Regular"
			this.brojKarata=0
			this.racunajCenu(m)
		},
		racunajCenu(m){
			switch(this.tipKarte){
				case "Regular":
					this.ukupnaCena=m.cena*this.brojKarata
					break
				case "Fan Pit":
					this.ukupnaCena=m.cena*this.brojKarata*2
					break
				case "VIP":
					this.ukupnaCena=m.cena*this.brojKarata*4
					break
				default:

			}
		},
		kupiKarte(m){
			if(this.brojKarata==0){
				alert("Nije moguće kupiti 0 karata")
			}else{
				m.slobodnaMesta-=this.brojKarata
				this.pripremiModal(m)
				alert("KUPLJENO")
			}

		},
	},
	mounted() {
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

		this.manifestacije.push({
			id:1,
			naziv: "Test1",
			grad: "Novi Sad",
			drzava: "Srbija",
			slika: "car1.jpg",
			tip: "Sport",
			datumPocetak: 1630620000000,
			cena: 1000,
			prosla: true,
			ocena: 4.4,
			brojMesta: 120,
			slobodnaMesta: 0,
			aktivna: true,
		})
		this.manifestacije.push({
			id:2,
			naziv: "Test2",
			grad: "Novi Sad",
			drzava: "Srbija",
			slika: "car2.jpg",
			tip: "Koncert",
			datumPocetak: 1630087200000,
			cena: 100,
			prosla: true,
			ocena: 3.2,
			brojMesta: 221,
			slobodnaMesta: 56,
			aktivna: true,
		})
		this.manifestacije.push({
			id:3,
			naziv: "Test3",
			grad: "Beograd",
			drzava: "Srbija",
			slika: "car3.jpg",
			tip: "Ostalo",
			datumPocetak: 1630864800000,
			cena: 1000,
			prosla: false,
			ocena: 0,
			brojMesta: 42,
			slobodnaMesta: 14,
			aktivna: true,
		})
		this.manifestacije.push({
			id:4,
			naziv: "Test4",
			grad: "Niš",
			drzava: "Srbija",
			slika: "man4.jpg",
			tip: "Predstava",
			datumPocetak: 1630864800000,
			cena: 3000,
			prosla: false,
			ocena: 0,
			brojMesta: 22,
			slobodnaMesta: 10,
			aktivna: true,
		})

		this.manifestacije.push({
			id:5,
			naziv: "Test5",
			grad: "Niš",
			drzava: "Srbija",
			slika: "man4.jpg",
			tip: "Predstava",
			datumPocetak: 1630620000000,
			cena: 1000,
			prosla: false,
			ocena: 0,
			brojMesta: 78,
			slobodnaMesta: 0,
			aktivna: true,
		})

		fixDate(this.manifestacije)
		this.manifestacije=this.manifestacije.sort(this.porediManifestacijePocetak)
		this.manifestacijeZaPrikaz = [...this.manifestacije]
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