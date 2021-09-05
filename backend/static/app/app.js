const HomePage = { template: '<home-page></home-page>' }
const RegisterPage = { template: '<register></register>' }
const UserPage = { template: '<user-page></user-page>' }


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: HomePage},
		{ path: '/register', component: RegisterPage},
		{path: '/user',component:UserPage},
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


