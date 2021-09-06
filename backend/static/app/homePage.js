function fixDate(students) {
	for (var s of students) {
		s.datumRodjenja = new Date(parseInt(s.datumRodjenja));
	}
	return students;
}

Vue.component("home-page", {
	data: function () {
		return {
			manifestacije: [],
			manifestacijeZaPrikaz: [],
			prikazRasprodatih: true,
			tipZaPrikaz: "Svi",
			tipovi: ["Svi", "Klubska žurka", "Koncert", "Predstava", "Izložba"],
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
		}
	},
	template: `
    <div>
    <div class="navbar-wrapper">
		<div class="container">
  
		  <nav class="navbar navbar-inverse navbar-static-top">
			<div class="container">
			  <div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				  <span class="sr-only">Toggle navigation</span>
				  <span class="icon-bar"></span>
				  <span class="icon-bar"></span>
				  <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Web Projekat Tim45</a>
			  </div>
			  <div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
				  <li class="active"><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
				  <li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
					<ul class="dropdown-menu">
					  <li><a href="#">Action</a></li>
					  <li><a href="#">Another action</a></li>
					  <li><a href="#">Something else here</a></li>
					  <li role="separator" class="divider"></li>
					  <li class="dropdown-header">Nav header</li>
					  <li><a href="#">Separated link</a></li>
					  <li><a href="#">One more separated link</a></li>
					</ul>
				  </li>
				</ul>


				<form class="navbar-form navbar-right" id="navForm">
					<div class="form-group">
					  <input type="text" placeholder="Username" class="form-control" id="username">
					</div>
					<div class="form-group">
					  <input type="password" placeholder="Password" class="form-control"  id="password">
					</div>
					<button @click="logIn()" type="submit" class="btn btn-success">Sign in</button>
				  </form>
				  </div>
			  </div>
		  </nav>
  
		</div>
	</div>

    <!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
		  <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		  <li data-target="#myCarousel" data-slide-to="1"></li>
		  <li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
		  <div class="item active">
			<img class="first-slide" src="images/car1.jpg" alt="First slide">
			<div class="container">
			  <div class="carousel-caption">
				<h1>Želiš da kupiš karte za sledeću manifestaciju?</h1>
				<p>Prijavi se danas i omogući sebi da na brz način obezbediš svoje mesto na sledećoj velikoj manifestaciji.</p>
				<p><a class="btn btn-lg btn-primary" href="#/register" role="button">Prijavi se danas</a></p>
			  </div>
			</div>
		  </div>
		  <div class="item">
			<img class="second-slide" src="images/car2.jpg" alt="Second slide">
			<div class="container">
			  <div class="carousel-caption">
				<h1>Šta raditi sledeći vikend?</h1>
				<p>U našoj ponudi je preko 1000 popularnih manifestacija.</p>
			  </div>
			</div>
		  </div>
		  <div class="item">
			<img class="third-slide" src="images/car3.jpg" alt="Third slide">
			<div class="container">
			  <div class="carousel-caption">
				<h1>Po nešto za svakoga.</h1>
				<p>Velika raznovrsnost manifestacija od koncerata i klubskih žurki do predstava i umetničkih izložbi.</p>
			  </div>
			</div>
		  </div>
		</div>
		<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
		  <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		  <span class="sr-only">Previous</span>
		</a>
		<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
		  <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		  <span class="sr-only">Next</span>
		</a>
	</div><!-- /.carousel -->

      <h1 style="text-align:center;margin:30px"> Manifestacije: </h1>

	  

	  <div class="container marketing">

		<div class="row">
		<div class="col-xs-2">
		<label class="checkbox-inline">
			<input type="checkbox" id="RasprodateCheckbox" v-model="prikazRasprodatih" @change="filtriraj()"> Prikaz rasprodatih
		</label>
	  	</div>
		  <div class="col-xs-3">
		  <div class="row">
		  <label>Tip manifestacije:</label>
		  <select v-model="tipZaPrikaz" @change="filtriraj()">
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
			<select v-model="sortirajPo" @change="sortiraj()">
			<option v-for="opcija in sortirajPoOpcije" v-bind:value="opcija">
				{{ opcija }}
			</option>
			</select>
			</div>
			</div>

			<div class="col-xs-3">
			<div class="row">
			<label>Red sortiranja:</label>
			<select v-model="redSortiranja" @change="sortiraj()">
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
        </div><!-- /.col-lg-4 -->
      </div><!-- /.row -->
		
  
	  </div><!-- /.container -->

      <div class="container marketing">
      	<footer>
		  <p class="pull-right"><a href="#">Back to top</a></p>
		  <p>&copy; 2021 TIM 45 </p>
		</footer>
      </div>
	</div>
    	  
`
	,
	methods: {
		proveriUsloveZaReset() {
			return this.prikazRasprodatih && this.tipZaPrikaz === "Svi"
		},
		filtriraj() {
			if (this.prikazRasprodatih) {
				this.manifestacijeZaPrikaz = [...this.manifestacije]
				if(this.redSortiranja!=="Bez reda"){
					this.sortiraj()
				}
			} else {
				this.manifestacijeZaPrikaz = this.manifestacije.filter(m => (m.slobodnaMesta==0) == this.prikazRasprodatih)
				if(this.redSortiranja!=="Bez reda"){
					this.sortiraj()
				}
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
			if(this.redSortiranja==="Bez reda"){
				this.filtriraj()
			}else{
				this.manifestacijeZaPrikaz.sort(this.porediManifestacije)
			}
		},
		trazi(){
			this.pretrazeno=true
			this.prikazRasprodatih=true
			this.tipZaPrikaz="Svi"
			this.sortirajPo="Naziv"
			this.redSortiranja="Bez reda"
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
		logIn(){
			if ( $('#navForm')[0].checkValidity() ) {
                $('#navForm').submit(function (evt) {
                    evt.preventDefault();
                    console.log(document.getElementById('username').value)

                    axios
                    .post('korisnici/login', {"username":document.getElementById('username').value, "password":document.getElementById('password').value})
                    .then(response => window.localStorage.setItem('jwt', response.data.JWTToken))
					
                    
                });
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

//		this.manifestacije.push({
//			id:1,
//			naziv: "Test1",
//			grad: "Novi Sad",
//			drzava: "Srbija",
//			slika: "car1.jpg",
//			tip: "Klubska žurka",
//			datumPocetak: 1630620000000,
//			cena: 1000,
//			prosla: true,
//			ocena: 4.4,
//			brojMesta: 120,
//			slobodnaMesta: 0,
//			aktivna: true,
//		})
//		this.manifestacije.push({
//			id:2,
//			naziv: "Test2",
//			grad: "Novi Sad",
//			drzava: "Srbija",
//			slika: "car2.jpg",
//			tip: "Koncert",
//			datumPocetak: 1630087200000,
//			cena: 100,
//			prosla: true,
//			ocena: 3.2,
//			brojMesta: 221,
//			slobodnaMesta: 56,
//			aktivna: true,
//		})
//		this.manifestacije.push({
//			id:3,
//			naziv: "Test3",
//			grad: "Beograd",
//			drzava: "Srbija",
//			slika: "car3.jpg",
//			tip: "Izložba",
//			datumPocetak: 1630864800000,
//			cena: 1000,
//			prosla: false,
//			ocena: 0,
//			brojMesta: 42,
//			slobodnaMesta: 3,
//			aktivna: false,
//		})
//		this.manifestacije.push({
//			id:4,
//			naziv: "Test4",
//			grad: "Niš",
//			drzava: "Srbija",
//			slika: "man4.jpg",
//			tip: "Predstava",
//			datumPocetak: 1630864800000,
//			cena: 3000,
//			prosla: false,
//			ocena: 0,
//			brojMesta: 22,
//			slobodnaMesta: 1,
//			aktivna: true,
//		})
//
//		this.manifestacije.push({
//			id:5,
//			naziv: "Test5",
//			grad: "Niš",
//			drzava: "Srbija",
//			slika: "man4.jpg",
//			tip: "Predstava",
//			datumPocetak: 1630620000000,
//			cena: 1000,
//			prosla: false,
//			ocena: 0,
//			brojMesta: 78,
//			slobodnaMesta: 0,
//			aktivna: true,
//		})
		
		axios.get(`/manifestacije`).then(response=>{
                     const man=[]
                     response.data.forEach(element => {
                         man.push({
                        	id:element.id,
                 			naziv: element.naziv,
                 			grad: element.lokacija.grad,
                 			drzava: element.lokacija.drzava,
                 			slika: element.slikaPath,
                 			tip: element.tip,
                 			datumPocetak: 1630620000000,
                 			cena: element.cenaRegular,
                 			prosla: false,
                 			ocena: element.ocena,
                 			brojMesta: element.brojMesta,
                 			slobodnaMesta: 0,
                 			aktivna: element.aktivna,
                         })
                         this.manifestacije=man
                         this.manifestacije=this.manifestacije.sort(this.porediManifestacijePocetak)
                 		this.manifestacijeZaPrikaz = [...this.manifestacije]
                 	
                     });
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