Vue.component("account-view", {
	data: function () {
		return {
            userRole: "",
            user: {},
		}
	},
    props:{
    },
	template: `
    <div>

    <div class="container marketing">

        <form class="form-signin" data-toggle="validator" id="formChange" role="form">
                <h2 class="form-signin-heading">Korisnički nalog:</h2>

                <div class="form-group">
                <label for="inputName" class="control-label">Ime</label>
                <input type="text" class="form-control" id="inputName" v-model="user.ime" data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                <label for="inputLastName" class="control-label">Prezime</label>
                <input type="text" class="form-control" id="inputLastName" v-model="user.prezime" data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                    <label for="inputPassword" class="control-label">Password</label>
                    <div class="form-inline row">
                    <div class="form-group col-sm-6">
                        <input type="password" data-minlength="6" v-model="user.password" class="form-control" id="inputPassword" placeholder="Šifra" data-error="Polje ne sme biti prazno" required>
                        <div class="help-block">Minimum 6 karaktera</div>
                    </div>
                    </div>
                </div>

                <label for="polRadio" class="control-label">Pol</label>
                <div class="form-group" id="polRadio">
                    
                    <div class="form-inline row">
                        <div class="form-group col-sm-4">
                            <div class="radio">
                            <label>
                                <input type="radio" value="MUSKI" name="pol" v-model="user.pol" required>
                                Muški
                            </label>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <div class="radio">
                            <label>
                                <input type="radio" value="ZENSKI" name="pol" v-model="user.pol" required>
                                Ženski
                            </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="datePicker">Datum rođenja</label>
                    <vuejs-datepicker id="datePicker" data-error="Polje ne sme biti prazno" v-model="user.datumRodjenja" format="dd.MM.yyyy" required></vuejs-datepicker> 
                </div>


                <button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="saveChanges()"><span id="pretragaIcon" class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span> Sačuvaj izmene</button>
            </form>

            <div v-if="userRole==='KUPAC'" class="form-group">
                <h4>Nivo kupca: {{user.tip}}</h4>
                <h4 v-if="user.tip==='NOVI'">Ostvaren popust 0% (sledeći nivo 3%)</h4>
                <h4 v-if="user.tip==='BRONZANI'">Ostvaren popust 3% (sledeći nivo 5%)</h4>
                <h4 v-if="user.tip==='SREBRNI'">Ostvaren popust 5% (sledeći nivo 10%)</h4>
                <h4 v-if="user.tip==='ZLATNI'">Ostvaren popust 10%</h4>

                <h4>Broj bodova: {{user.brojBodova}}</h4>
                <h4 v-if="user.tip==='NOVI'">Potrebno za sledeći nivo: {{2000-user.brojBodova}}</h4>
                <h4 v-if="user.tip==='BRONZANI'">Potrebno za sledeći nivo: {{3000-user.brojBodova}}</h4>
                <h4 v-if="user.tip==='SREBRNI'">Potrebno za sledeći nivo: {{4000-user.brojBodova}}</h4>
            </div>

	</div>
    </div>
    	  
`
	,
	methods: {
        async saveChanges(){
            if ( $('#formChange')[0].checkValidity() ) {
                $('#formChange').submit(function (evt) {
                    evt.preventDefault();
                    
                });
                await axios.post(`korisnici/izmena`,{
                    ime:this.user.ime,
                    prezime:this.user.prezime,
                    datumRodjenja:this.user.datumRodjenja.getTime(),
                    password:this.user.password,
                    username: window.localStorage.getItem("username"),
                    pol: this.user.pol,
                    uloga:this.userRole
                },{ headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    alert("Izmene uspešno sačuvane!")
                }).catch(err=>{
                    alert("Došlo je do greške!")
                })
                    
            }
        },
	},
	async mounted() {
        this.userRole=window.localStorage.getItem('uloga')
        let username=window.localStorage.getItem('username')

        switch(this.userRole){
            case "KUPAC":
                await axios.get(`/kupci/`+username,{ data:{},headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    console.log(response.data)
                    this.user=response.data
                })
                break
            case "PRODAVAC":
                await axios.get(`/prodavci/`+username,{ data:{},headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    console.log(response.data)
                    this.user=response.data
                })
                break
            case "ADMINISTRATOR":
                await axios.get(`/administratori/`+username,{data:{}, headers: {"Authorization" : `Bearer ${window.localStorage.getItem("jwt")}`} }).then(response=>{
                    console.log(response.data)
                    this.user=response.data
                })
                break
            default:
        }

        this.user.datumRodjenja=new Date(parseInt(this.user.datumRodjenja))

	},
	components:{
		vuejsDatepicker,
	}
});