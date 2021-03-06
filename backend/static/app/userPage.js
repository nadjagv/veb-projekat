


Vue.component("user-page", {
	data: function () {
		return {
			prikaz: "Home",
		}
	},
	template: `
    <div>
		  <nav class="navbar navbar-inverse navbar-static-top">
			<div class="container">
			  <div class="navbar-header">
			  </div>
			  <div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
				  <li class="active"><a @click="prikaziHome()"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
				  <li class="active"><a @click="prikaziAccount()"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Korisnički nalog</a></li>
				  <li class="active"><a @click="prikaziKarte()"><span class="glyphicon glyphicon-th-large" aria-hidden="true"></span> Karte</a></li>
                  <li class="active navbar-right"><a @click="logOut()" href="javascript:history.back()"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Log out</a></li>
				</ul>


			  </div>
			</div>
		  </nav>
		  
		  <karte-view v-if="prikaz==='Karte'" ></karte-view>
		  <man-view-user v-if="prikaz==='Home'" ></man-view-user>
          <account-view v-if="prikaz==='Account'" ></account-view>
		  

		  <div class="container marketing">
				<footer>
				<p class="pull-right"><a href="#">Back to top</a></p>
				<p>&copy; 2021 TIM 45 </p>
				</footer>
			</div>
			</div>

	</div>
    	  
`
	,
	methods: {
		prikaziHome(){
			this.prikaz="Home"
		},
		prikaziAccount(){
			this.prikaz="Account"
		},
		prikaziKarte(){
			this.prikaz="Karte"
		},
		async logOut(){
			await axios.post(`korisnici/logout/` + window.localStorage.getItem("username"),{data:{}},{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })
			
			window.localStorage.removeItem('uloga')
			window.localStorage.removeItem('username')
			window.localStorage.removeItem('jwt')}
	},
	mounted() {
		
	},
	components:{
		KarteView,
        TestPage,
        ManView,
		AccountView,
	}
});