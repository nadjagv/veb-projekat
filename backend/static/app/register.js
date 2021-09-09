Vue.component("register", {
	data: function () {
		return {
            newUser:{}
		}
	},
	template: `
    <div>

        <div class="container">

            <form class="form-signin" data-toggle="validator" id="formRegister" role="form">
                <h2 class="form-signin-heading">Registracija</h2>

                <div class="form-group">
                <label for="inputName" class="control-label">Ime</label>
                <input type="text" class="form-control" id="inputName" v-model="newUser.ime" data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                <label for="inputLastName" class="control-label">Prezime</label>
                <input type="text" class="form-control" id="inputLastName" v-model="newUser.prezime" data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                    <label for="inputUsername" class="control-label">Korisničko ime</label>
                    <input type="text" autocomplete="new-username" pattern="^[_A-z0-9]{1,}$" maxlength="15" v-model="newUser.username" class="form-control" id="inputUsername"  data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                    <label for="inputPassword" class="control-label">Password</label>
                    <div class="form-inline row">
                    <div class="form-group col-sm-6">
                        <input type="password" autocomplete="new-password" data-minlength="6" v-model="newUser.password" class="form-control" id="inputPassword" placeholder="Šifra" data-error="Polje ne sme biti prazno" required>
                        <div class="help-block">Minimum 6 karaktera</div>
                    </div>
                    <div class="form-group col-sm-6">
                        <input type="password" class="form-control" id="inputPasswordConfirm" data-match="#inputPassword" data-match-error="Šifre se ne poklapaju" data-error="Polje ne sme biti prazno" placeholder="Potvrdi" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    </div>
                </div>

                <label for="polRadio" class="control-label">Pol</label>
                <div class="form-group" id="polRadio">
                    
                    <div class="form-inline row">
                        <div class="form-group col-sm-4">
                            <div class="radio">
                            <label>
                                <input type="radio" value="MUSKI" v-model="newUser.pol" name="pol" required>
                                Muški
                            </label>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                        <div class="radio">
                            <label>
                                <input type="radio" value="ZENSKI" v-model="newUser.pol" name="pol" required>
                                Ženski
                            </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="datePicker">Datum rođenja</label>
                    <vuejs-datepicker id="datePicker" data-error="Polje ne sme biti prazno"  v-model="newUser.datumRodjenja" format="dd.MM.yyyy" required></vuejs-datepicker> 
                    <div class="help-block with-errors"></div>
                </div>


                <button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="submitReg()">Registruj se</button>
            </form>

        </div>
	</div>
    	  
`
	,

	methods: {
        async submitReg(){
            if ( $('#formRegister')[0].checkValidity() ) {
                $('#formRegister').submit(function (evt) {
                    evt.preventDefault();
                });


                await axios.post(`korisnici/registracija`,{
                    ime: this.newUser.ime,
                    prezime: this.newUser.prezime,
                    username: this.newUser.username,
                    password:this.newUser.password,
                    pol: this.newUser.pol,
                    datumRodjenja: this.newUser.datumRodjenja.getTime(),
                    uloga: "KUPAC",
                }).then(response=>{
                    alert("Uspešna registracija!")
                    window.history.back();
                }).catch(err=>{
                    alert("Došlo je do greške!")
                })
            }
        },
	},
	mounted() {

	},
    components:{
		vuejsDatepicker,
	},
});
