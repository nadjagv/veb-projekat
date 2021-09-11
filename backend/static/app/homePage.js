const TestPage = { template: '<test-page></test-page>' }
const ManView = { template: '<man-view-user></man-view-user>' }
const AccountView = { template: '<account-view></account-view>' }
const KarteView = { template: '<karte-view></karte-view>' }
const UserListView = { template: '<user-list-view></user-list-view>' }

Vue.component("home-page", {
	data: function () {
		return {
			manifestacije: [],
			manifestacijeZaPrikaz: [],
			prikazRasprodatih: true,
			tipZaPrikaz: "Svi",
			tipovi: ["Svi", "KONCERT", "FESTIVAL", "PREDSTAVA", "SPORT", "OSTALO"],
			redSortiranjaOpcije:["Bez reda","Opadajuće","Rastuće"],
			redSortiranja:"Bez reda",
			sortirajPoOpcije:["Naziv","Datum","Cena","Lokacija"],
			komentari:[],
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


		<man-view-user  ></man-view-user>	

      

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
		
		logIn(){
			if ( $('#navForm')[0].checkValidity() ) {
                $('#navForm').submit(function (evt) {
                    evt.preventDefault();
                    console.log(document.getElementById('username').value)

                    axios
                    .post('korisnici/login', {"username":document.getElementById('username').value, "password":document.getElementById('password').value})
                    .then(response => {
						console.log(response.data)
						window.localStorage.setItem('uloga',response.data.uloga)
						window.localStorage.setItem('username',response.data.username)
						window.localStorage.setItem('jwt', response.data.JWTToken)

						if(response.data.uloga==="KUPAC"){
							alert("Uspešno ulogovan kao kupac!")
							window.location="#/user"
						}else if(response.data.uloga==="PRODAVAC"){
							alert("Uspešno ulogovan kao prodavac!")
							window.location="#/seller"
						}else if(response.data.uloga==="ADMINISTRATOR"){
							alert("Uspešno ulogovan kao administrator!")
							window.location="#/admin"
						}
					}).catch(err=>{
						alert("Loši kredencijali!")
					})
					
                    
                });
            }
		},
	},
	async mounted() {

		window.localStorage.removeItem('uloga')
		window.localStorage.removeItem('username')
		window.localStorage.removeItem('jwt')

		
		},
	filters: {
		dateFormat: function (value, format) {
			var parsed = moment(value);
			return parsed.format(format);
		}
	},
	components:{
		ManView,
		vuejsDatepicker,
	}
});