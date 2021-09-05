function fixDate(students) {
	for (var s of students) {
		s.datumRodjenja = new Date(parseInt(s.datumRodjenja));
	}
	return students;
}

const TestPage = { template: '<test-page></test-page>' }
const ManView = { template: '<man-view-user></man-view-user>' }

Vue.component("user-page", {
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
                  <li class="active navbar-right"><a href="javascript:history.back()"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Log out</a></li>
				</ul>


			  </div>
			</div>
		  </nav>

          <man-view-user></man-view-user>

	</div>
    	  
`
	,
	methods: {
		
	},
	mounted() {
		
	},
	components:{
		vuejsDatepicker,
        TestPage,
        ManView,
	}
});