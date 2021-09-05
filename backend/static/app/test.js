Vue.component("test-page", {
	data: function () {
		return {
		}
	},
	template: `
    <div>
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
	</div>
    	  
`
	,
	methods: {
		
	},
	mounted() {
		
	},
	filters: {
		dateFormat: function (value, format) {
			var parsed = moment(value);
			return parsed.format(format);
		}
	},
});