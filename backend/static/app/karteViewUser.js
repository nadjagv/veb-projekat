function fixDate(karte) {
	for (var k of karte) {
		k.datumPocetak = new Date(parseInt(k.datumPocetak));
	}
	return karte;
}

Vue.component("karte-view", {
	data: function () {
		return {
            karte:[],
            karteZaPrikaz:[],
            statusZaPrikaz:"Svi",
            tipZaPrikaz:"Svi",
            statusi: ["Svi","Rezervisana","Odustanak"],
            tipovi: ["Svi","Regular","Fan Pit","VIP"],
            sortirajPo:"Naziv manifestacije",
            sortirajPoOpcije:["Naziv manifestacije","Cena","Datum"],
            redSortiranjaOpcije:["Bez reda","Opadajuće","Rastuće"],
			redSortiranja:"Bez reda",
            pretrazeno: false,
			pretragaNaziv:"",
			pretragaCenaOd:0,
			pretragaCenaDo:100000,
			pretragaDatumOd: Date.now(),
			pretragaDatumDo: Date.now(),
            userRole: "",
		}
	},
    props:{
	},
	template: `
    <div>

    <div class="container marketing">

    <h1 style="text-align:center;margin:30px"> Karte: </h1>

    <div class="row">

        <div class="col-xs-2">
            <div class="row">
                <label>Status karte:</label>
                <select v-model="statusZaPrikaz" @change="pripremi()">
                    <option v-for="status in statusi" v-bind:value="status">
                        {{ status }}
                    </option>
                </select>
            </div>
        </div>

        <div class="col-xs-3">
            <div class="row">
                <label>Tip karte:</label>
                <select v-model="tipZaPrikaz" @change="pripremi()">
                    <option v-for="tip in tipovi" v-bind:value="tip">
                        {{ tip }}
                    </option>
                </select>
            </div>
        </div>

	</div>
			
	<div class="row">
		<div class="col-xs-3">
            <div class="row">
                <label>Sortiraj po:</label>
                <select v-model="sortirajPo" @change="pripremi()">
                <option v-for="opcija in sortirajPoOpcije" v-bind:value="opcija">
                    {{ opcija }}
                </option>
                </select>
            </div>
		</div>

		<div class="col-xs-4">
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
                    <button class="btn " v-if="pretrazeno" type="button" @click="reset1()">
                    Reset <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                    </button>
                </h5>
            </div>

			<div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
				<div class="card-body">
					<label>Naziv manifestacije:</label>
					<input type="text" v-model="pretragaNaziv" /> 
					<br />
					<label>Cena</label>
					<label>od:</label>
					<input type="number" v-model="pretragaCenaOd" min="0" max="100000"/> 
					<label>do:</label>
					<input type="number" v-model="pretragaCenaDo" min="0" max="100000"/> 
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
        <div class="col-lg-3" v-for="k in karteZaPrikaz" style="margin:20px;border-style:solid">
            <h2>{{k.naziv}}</h2>
            <h3>{{k.tip}}</h3>
            <h3>{{k.status}}</h3>
            <p >Datum: {{k.datumPocetak | dateFormat('HH:mm DD.MM.YYYY')}}</p>
            <p>Cena: {{k.cena}}RSD </p>
            <p>Broj karata: {{k.brojKarata}}</p>

            <button @click="odustani(k)" v-if="userRole==='KUPAC' && k.status=='Rezervisana'" type="button" style="margin-bottom:10px" class="btn btn-danger" >
			Odustani <span class="glyphicon glyphicon-cancel" aria-hidden="true"></span>
			</button>

        </div>

    </div>

	</div>
    </div>
    	  
`
	,
	methods: {
        pripremi(){

            this.karteZaPrikaz = [...this.karte]

            if(this.pretrazeno){
                this.trazi()
            }
            this.filtriraj()
            this.sortiraj()
        },
		filtriraj(){
            if(this.tipZaPrikaz!=="Svi"){
                this.karteZaPrikaz=this.karteZaPrikaz.filter(k=>k.tip===this.tipZaPrikaz)
            }

            if(this.statusZaPrikaz!=="Svi"){
                this.karteZaPrikaz=this.karteZaPrikaz.filter(k=>k.status===this.statusZaPrikaz)
            }

            
        },
        porediKarte(k1,k2){
			let k1Fix=""
			let k2Fix=""
			switch(this.sortirajPo) {
				case "Naziv manifestacije":
				  k1Fix=k1.naziv.toUpperCase()
				  k2Fix=k2.naziv.toUpperCase()
				  break;
				case "Cena":
					k1Fix=k1.cena
					k2Fix=k2.cena
				  break;
				case "Datum":
					k1Fix=k1.datumPocetak
					k2Fix=k2.datumPocetak
				  break;
				default:	  
			}
			let comparison = 0;
			if (k1Fix > k2Fix) {
			  comparison = 1;
			} else if (k1Fix < k2Fix) {
			  comparison = -1;
			}

			if(this.redSortiranja=="Opadajuće"){
				return comparison* -1;
			}else{
				return comparison;
			}
		},
        sortiraj(){
            if(this.redSortiranja!=="Bez reda"){
				this.karteZaPrikaz.sort(this.porediKarte)
			}
        },
        trazi(){
            if(!this.pretrazeno){
                this.pretrazeno=true
			    this.tipZaPrikaz="Svi"
                this.statusZaPrikaz="Svi"
			    this.sortirajPo="Naziv manifestacije"
			    this.redSortiranja="Bez reda"
            }

			this.karteZaPrikaz =this.karte.filter(k=>k.naziv.toUpperCase().includes(this.pretragaNaziv.toUpperCase()))
			this.karteZaPrikaz =this.karteZaPrikaz.filter(k=>k.cena<=this.pretragaCenaDo && k.cena>=this.pretragaCenaOd)
			this.karteZaPrikaz =this.karteZaPrikaz.filter(k=>k.datumPocetak<=this.pretragaDatumDo && k.datumPocetak>=this.pretragaDatumOd)
		},
        reset1(){
            this.pretrazeno=false
			this.tipZaPrikaz="Svi"
            this.statusZaPrikaz="Svi"
			this.sortirajPo="Naziv manifestacije"
			this.redSortiranja="Bez reda"
			this.pretragaNaziv=""
            this.pretragaCenaDo=100000
			this.pretragaCenaOd=0
			this.pretragaDatumDo=Date.now()
			this.pretragaDatumOd=Date.now()
            this.karteZaPrikaz= [...this.karte]
        },
        dropdownPretraga(){
            $("#pretragaIcon").toggleClass("glyphicon-arrow-down");
			$("#pretragaIcon").toggleClass("glyphicon-arrow-up");
        },
        odustani(k){
            k.status="Odustanak"
        },
	},
	mounted() {

        this.userRole=window.localStorage.getItem('uloga')

        //TO DO: ucitavanje karata na osnovu user role

        if(this.userRole==="KUPAC" || this.userRole==="ADMINISTRATOR"){
            this.karte.push({
                id:1,
                naziv:"Test 1",
                datumPocetak: 1630620000000,
                brojKarata: 5,
                cena: 5000,
                tip: "Regular",
                status: "Rezervisana",
            })
            
            this.karte.push({
                id:2,
                naziv:"Test 2",
                datumPocetak: 1630620000000,
                brojKarata: 3,
                cena: 6000,
                tip: "VIP",
                status: "Odustanak",
            })
    
            this.karte.push({
                id:3,
                naziv:"Test 3",
                datumPocetak: 1630620000000,
                brojKarata: 3,
                cena: 9000,
                tip: "VIP",
                status: "Odustanak",
            })
    
            this.karte.push({
                id:4,
                naziv:"Test 4",
                datumPocetak: 1630620000000,
                brojKarata: 4,
                cena: 8000,
                tip: "Fan Pit",
                status: "Odustanak",
            })
    
            this.karte.push({
                id:5,
                naziv:"Test 5",
                datumPocetak: 1630620000000,
                brojKarata: 4,
                cena: 16000,
                tip: "VIP",
                status: "Rezervisana",
            })
    
        }else{
            this.karte.push({
                id:1,
                naziv:"Test 1",
                datumPocetak: 1630620000000,
                brojKarata: 5,
                cena: 5000,
                tip: "Regular",
                status: "Rezervisana",
            })

            this.karte.push({
                id:5,
                naziv:"Test 5",
                datumPocetak: 1630620000000,
                brojKarata: 4,
                cena: 16000,
                tip: "VIP",
                status: "Rezervisana",
            })
        }

        
        this.karteZaPrikaz = [...this.karte]
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