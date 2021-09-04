const HomePage = { template: '<home-page></home-page>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: HomePage},
	  ]
});

Vue.component('star-rating', VueStarRating.default);

var app = new Vue({
	router,
	el: '#webProjekat',
	components: {
		VueStarRating,
		
  },
});


