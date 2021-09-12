function fixDateKarte(karte) {
	for (var k of karte) {
		k.datumVremeOdrzavanja = new Date(parseInt(k.datumVremeOdrzavanja));
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
            statusi: ["Svi","REZERVISANA","ODUSTANAK"],
            tipovi: ["Svi","REGULAR","FAN_PIT","VIP"],
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

        <div class="col-xs-3">
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
            <h2>{{k.nazivManifestacije}}</h2>
            <h3>{{k.tip}}</h3>
            <h3>{{k.status}}</h3>
            <p >Datum: {{k.datumVremeOdrzavanja | dateFormat('HH:mm DD.MM.YYYY')}}</p>
            <p>Cena: {{k.cena}}RSD </p>
            <p>Broj karata: {{k.brojKarata}}</p>
            <p v-if="userRole!=='KUPAC'">Kupac: {{k.kupacUsername}}</p>

            <button @click="odustani(k)" v-if="userRole==='KUPAC' && k.status=='REZERVISANA'" type="button" style="margin-bottom:10px" class="btn btn-danger" >
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
				  k1Fix=k1.nazivManifestacije.toUpperCase()
				  k2Fix=k2.nazivManifestacije.toUpperCase()
				  break;
				case "Cena":
					k1Fix=k1.cena
					k2Fix=k2.cena
				  break;
				case "Datum":
					k1Fix=k1.datumVremeOdrzavanja
					k2Fix=k2.datumVremeOdrzavanja
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

			this.karteZaPrikaz =this.karte.filter(k=>k.nazivManifestacije.toUpperCase().includes(this.pretragaNaziv.toUpperCase()))
			this.karteZaPrikaz =this.karteZaPrikaz.filter(k=>k.cena<=this.pretragaCenaDo && k.cena>=this.pretragaCenaOd)
			this.karteZaPrikaz =this.karteZaPrikaz.filter(k=>k.datumVremeOdrzavanja<=this.pretragaDatumDo && k.datumVremeOdrzavanja>=this.pretragaDatumOd)
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
        async odustani(k){
            await axios.post(`/karte/otkazi`,{
                manifestacijaId: k.manifestacijaId,
				nazivManifestacije:k.nazivManifestacije,
				datumVremeOdrzavanja:k.datumVremeOdrzavanja.getTime(),
				cena: k.cena,
				kupacUsername:k.kupacUsername,
				tip: k.tip,
				brojKarata: k.brojKarata,
            },{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} })
            k.status="Odustanak"
        },
	},
	async mounted() {

        this.userRole=window.localStorage.getItem('uloga')

        //TO DO: ucitavanje karata na osnovu user role

        switch(this.userRole){
            case "KUPAC":
                
                await axios.get(`/kupci/mojeKarteSve/`+window.localStorage.getItem('username'),{ data:{},headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    const kar=[]
                    
                    response.data.forEach(element => {
                        console.log(element.datumVremeOdrzavanja)
                        kar.push({
                           id:element.id,
                           manifestacijaId:element.manifestacijaId,
                           datumVremeOdrzavanja:element.datumVremeOdrzavanja,
                           brojKarata:element.brojKarata,
                           cena: element.cena,
                           kupacUsername: element.kupacUsername,
                           status: element.status,
                           tip: element.tip,
                           nazivManifestacije: element.nazivManifestacije,
                        })
                        
                    });
                    this.karte=kar
                    fixDateKarte(this.karte)
                    this.karteZaPrikaz = [...this.karte]
                
                })
                break
            case "PRODAVAC":
                await axios.get(`/prodavci/mojeKarte/`+window.localStorage.getItem('username'),{ data:{},headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    const kar=[]
                    
                    response.data.forEach(element => {
                        console.log(element.datumVremeOdrzavanja)
                        kar.push({
                           id:element.id,
                           manifestacijaId:element.manifestacijaId,
                           datumVremeOdrzavanja:element.datumVremeOdrzavanja,
                           brojKarata:element.brojKarata,
                           cena: element.cena,
                           kupacUsername: element.kupacUsername,
                           status: element.status,
                           tip: element.tip,
                           nazivManifestacije: element.nazivManifestacije,
                        })
                        
                    });
                    this.karte=kar
                    fixDateKarte(this.karte)
                    this.karteZaPrikaz = [...this.karte]
                
                })
                break
            case "ADMINISTRATOR":
                await axios.get(`/karte`,{data:{}, headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    const kar=[]
                    
                    response.data.forEach(element => {
                        console.log(element.datumVremeOdrzavanja)
                        kar.push({
                           id:element.id,
                           manifestacijaId:element.manifestacijaId,
                           datumVremeOdrzavanja:element.datumVremeOdrzavanja,
                           brojKarata:element.brojKarata,
                           cena: element.cena,
                           kupacUsername: element.kupacUsername,
                           status: element.status,
                           tip: element.tip,
                           nazivManifestacije: element.nazivManifestacije,
                        })
                        
                    });
                    this.karte=kar
                    fixDateKarte(this.karte)
                    this.karteZaPrikaz = [...this.karte]
                
                })
                break
            default:
        }

        

        
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