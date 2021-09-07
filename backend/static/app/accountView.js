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

        <form class="form-signin" data-toggle="validator" id="formRegister" role="form">
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

                <!--<div class="form-group">
                    <label for="inputPassword" class="control-label">Password</label>
                    <div class="form-inline row">
                    <div class="form-group col-sm-6">
                        <input type="password" data-minlength="6" class="form-control" id="inputPassword" placeholder="Šifra" data-error="Polje ne sme biti prazno" required>
                        <div class="help-block">Minimum 6 karaktera</div>
                    </div>
                    <div class="form-group col-sm-6">
                        <input type="password" class="form-control" id="inputPasswordConfirm" data-match="#inputPassword" data-match-error="Šifre se ne poklapaju" data-error="Polje ne sme biti prazno" placeholder="Potvrdi" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    </div>
                </div>-->

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
                    <input type="date" id="datePicker" 
                        min="1900-01-01" v-model="user.datumRodjenja" data-error="Polje ne sme biti prazno" required>
                        <div class="help-block with-errors"></div>
                </div>


                <button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="submitReg()"><span id="pretragaIcon" class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span> Sačuvaj izmene</button>
            </form>

	</div>
    </div>
    	  
`
	,
	methods: {
		
	},
	mounted() {
        this.userRole=window.localStorage.getItem('uloga')
        let username=window.localStorage.getItem('username')

        switch(this.userRole){
            case "KUPAC":
                axios.get(`/kupci/`+username).then(response=>{
                    console.log(response.data)
                    this.user=response.data
                })
                break
            case "PRODAVAC":
                axios.get(`/prodavci/`+username).then(response=>{
                    console.log(response.data)
                    this.user=response.data
                })
                break
            case "ADMINISTRATOR":
                axios.get(`/administratori/`+username).then(response=>{
                    console.log(response.data)
                    this.user=response.data
                })
                break
            default:
        }
	},
	components:{
		vuejsDatepicker,
	}
});