Vue.component("user-list-view", {
	data: function () {
		return {
            korisnici:[],
            korisniciZaPrikaz:[],
            ulogaZaPrikaz:"Sve",
            tipZaPrikaz:"Svi",
            uloge: ["Sve","KUPAC","PRODAVAC","ADMINISTRATOR"],
            tipovi: ["Svi","ZLATNI","SREBRNI","BRONZANI"],
            sortirajPo:"Ime",
            sortirajPoOpcije:["Ime","Prezime","Korisničko ime","Broj bodova"],
            redSortiranjaOpcije:["Bez reda","Opadajuće","Rastuće"],
			redSortiranja:"Bez reda",
            pretrazeno: false,
			pretragaIme:"",
            pretragaPrezime:"",
            pretragaUsername:"",
            userRole: "",
            username:"",
		}
	},
    props:{
	},
	template: `
    <div>

    <div class="container marketing">

    <h1 style="text-align:center;margin:30px"> Korisnici: </h1>

    <div class="row">

        <div class="col-xs-2">
            <div class="row">
                <label>Uloga:</label>
                <select v-model="ulogaZaPrikaz" @change="pripremi()">
                    <option v-for="uloga in uloge" v-bind:value="uloga">
                        {{ uloga }}
                    </option>
                </select>
            </div>
        </div>

        <div class="col-xs-3">
            <div class="row">
                <label>Tip kupca:</label>
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
                    <button class="btn " v-if="pretrazeno" type="button" @click="reset()">
                    Reset <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                    </button>
                </h5>
            </div>

			<div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
				<div class="card-body">

					<label>Ime:</label>
					<input type="text" v-model="pretragaIme" /> 
					<br />

                    <label>Prezime:</label>
					<input type="text" v-model="pretragaPrezime" /> 
					<br />

                    <label>Korisničko ime:</label>
					<input type="text" v-model="pretragaUsername" /> 
					<br />
					
					<button class="btn " type="button" @click="trazi()" >
						Traži <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					</button>
				</div>
				</div>
			</div>
	</div>

	<div class="row-cols-3 justify-content-center">
        <div class="col-lg-3" v-for="k in korisniciZaPrikaz" style="margin:20px;border-style:solid">
            <h2>{{k.ime}} {{k.prezime}}</h2>
            <h3>{{k.uloga}}</h3>
            <h3>{{k.status}}</h3>

            <p>Korisničko ime: {{k.username}}</p>
            <p>Datum rođenja: {{k.datum}}</p>

            <p v-if="k.uloga==='KUPAC'">Tip kupca:{{k.tip}}</p>
            <p v-if="k.uloga==='KUPAC'">Broj bodova:{{k.bodovi}}</p>
            <p v-if="k.uloga==='KUPAC' && k.blokiran">Status kupca: Blokiran </p>
            <p v-if="k.uloga==='KUPAC' && !k.blokiran">Status kupca: Aktivan </p>

            <button @click="blokiraj(k)" v-if="userRole==='ADMINISTRATOR' && k.uloga==='KUPAC' && !k.blokiran" type="button" style="margin-bottom:10px" class="btn btn-warning" >
			Blokiraj <span class="glyphicon glyphicon-stop" aria-hidden="true"></span>
			</button>

            <button @click="obrisi(k)" v-if="userRole==='ADMINISTRATOR'" type="button" style="margin-bottom:10px" class="btn btn-danger" >
			Obriši <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
			</button>

        </div>

    </div>

	</div>
    </div>
    	  
`
	,
	methods: {
        blokiraj(k){
            k.blokiran=true
        },
        obrisi(k){
            this.korisnici=this.korisnici.filter(kor=>kor.username!=k.username)
            this.korisniciZaPrikaz=this.korisniciZaPrikaz.filter(kor=>kor.username!=k.username)
        },
        pripremi(){

            this.korisniciZaPrikaz = [...this.korisnici]

            if(this.pretrazeno){
                this.trazi()
            }
            this.filtriraj()
            this.sortiraj()
        },
		filtriraj(){
            if(this.tipZaPrikaz!=="Svi"){
                this.korisniciZaPrikaz=this.korisniciZaPrikaz.filter(k=>k.tip===this.tipZaPrikaz)
            }

            if(this.ulogaZaPrikaz!=="Sve"){
                this.korisniciZaPrikaz=this.korisniciZaPrikaz.filter(k=>k.uloga===this.ulogaZaPrikaz)
            }

            
        },
        porediKorisnike(k1,k2){
			let k1Fix=""
			let k2Fix=""
			switch(this.sortirajPo) {
				case "Ime":
				  k1Fix=k1.ime.toUpperCase()
				  k2Fix=k2.ime.toUpperCase()
				  break;
				case "Prezime":
					k1Fix=k1.prezime.toUpperCase()
				    k2Fix=k2.prezime.toUpperCase()
				  break;
				case "Korisničko ime":
					k1Fix=k1.username
				    k2Fix=k2.username
				  break;
                case "Broj bodova":
                    if(k1.uloga!=="KUPAC"){
                        k1Fix=0
                    }else{
                        k1Fix=k1.bodovi
                    }
                    if(k2.uloga!=="KUPAC"){
                        k2Fix=0
                    }else{
                        k2Fix=k1.bodovi
                    }
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
				this.korisniciZaPrikaz.sort(this.porediKorisnike)
			}
        },
        trazi(){
            if(!this.pretrazeno){
                this.pretrazeno=true
			    this.tipZaPrikaz="Svi"
                this.ulogaZaPrikaz="Sve"
			    this.sortirajPo="Ime"
			    this.redSortiranja="Bez reda"
            }

            this.korisniciZaPrikaz=this.korisnici.filter(k=>k.ime.toUpperCase().includes(this.pretragaIme.toUpperCase()) && k.prezime.toUpperCase().includes(this.pretragaPrezime.toUpperCase()) && k.username.includes(this.pretragaUsername))

		},
        reset(){
            this.pretrazeno=false
			this.tipZaPrikaz="Svi"
            this.ulogaZaPrikaz="Sve"
			this.sortirajPo="Ime"
			this.redSortiranja="Bez reda"
			this.pretragaIme=""
            this.pretragaPrezime=""
            this.pretragaUsername=""
            this.korisniciZaPrikaz= [...this.korisnici]
        },
        dropdownPretraga(){
            $("#pretragaIcon").toggleClass("glyphicon-arrow-down");
			$("#pretragaIcon").toggleClass("glyphicon-arrow-up");
        },
        
	},
	async mounted() {

        this.userRole=window.localStorage.getItem('uloga')
        let usernameMain=window.localStorage.getItem('username')

        if(this.userRole==="ADMINISTRATOR"){
            await axios.get(`/prodavci`).then(response=>{
                response.data.forEach(element=>{
                    this.korisnici.push({
                        ime: element.ime,
                        prezime: element.prezime,
                        uloga: element.uloga,
                        username: element.username,
                        datum: element.datumRodjenja,
                    })
                })
            })

            await axios.get(`/kupci`).then(response=>{
                response.data.forEach(element=>{
                    this.korisnici.push({
                        ime: element.ime,
                        prezime: element.prezime,
                        uloga: element.uloga,
                        username: element.username,
                        datum: element.datumRodjenja,
                        tip: element.tip,
                        bodovi: element.brojBodova,
                        blokiran: element.blokiran,
                    })
                })
            })

            await axios.get(`/administratori`).then(response=>{
                response.data.forEach(element=>{
                    this.korisnici.push({
                        ime: element.ime,
                        prezime: element.prezime,
                        uloga: element.uloga,
                        username: element.username,
                        datum: element.datumRodjenja,
                    })
                })
            })

        }else if(this.userRole==="PRODAVAC"){
            await axios.get(`/prodavci/mojiKupci/`+usernameMain).then(response=>{
                response.data.forEach(element=>{
                    this.korisnici.push({
                        ime: element.ime,
                        prezime: element.prezime,
                        uloga: element.uloga,
                        username: element.username,
                        datum: element.datumRodjenja,
                        tip: element.tip,
                        bodovi: element.brojBodova,
                        blokiran: element.blokiran,
                    })
                })
            })
        }

        this.korisniciZaPrikaz = [...this.korisnici]
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