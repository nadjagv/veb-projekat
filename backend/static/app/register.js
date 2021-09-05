Vue.component("register", {
	data: function () {
		return {
		}
	},
	template: `
    <div>

        <div class="container">

            <form class="form-signin" data-toggle="validator" id="formRegister" role="form">
                <h2 class="form-signin-heading">Registracija</h2>

                <div class="form-group">
                <label for="inputName" class="control-label">Ime</label>
                <input type="text" class="form-control" id="inputName" data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                <label for="inputLastName" class="control-label">Prezime</label>
                <input type="text" class="form-control" id="inputLastName" data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                    <label for="inputUsername" class="control-label">Korisničko ime</label>
                    <input type="text" pattern="^[_A-z0-9]{1,}$" maxlength="15" class="form-control" id="inputUsername"  data-error="Polje ne sme biti prazno" required>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
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
                </div>

                <label for="polRadio" class="control-label">Pol</label>
                <div class="form-group" id="polRadio">
                    
                    <div class="form-inline row">
                        <div class="form-group col-sm-4">
                            <div class="radio">
                            <label>
                                <input type="radio" name="pol" required>
                                Muški
                            </label>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                        <div class="radio">
                            <label>
                                <input type="radio" name="pol" required>
                                Ženski
                            </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="datePicker">Datum rođenja</label>
                    <input type="date" id="datePicker" 
                        min="1900-01-01" data-error="Polje ne sme biti prazno" required>
                        <div class="help-block with-errors"></div>
                </div>


                <button class="btn btn-lg btn-primary" style="margin:20px" type="submit" @click="submitReg()">Registruj se</button>
            </form>

        </div>
	</div>
    	  
`
	,

	methods: {
        submitReg(){
            if ( $('#formRegister')[0].checkValidity() ) {
                alert("Uspesna registracija")
                $('#formRegister').submit(function (evt) {
                    evt.preventDefault();
                    window.history.back();
                });
            }
        },
	},
	mounted() {

	},
    components:{
		vuejsDatepicker,
	},
});
