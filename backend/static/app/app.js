const HomePage = { template: '<home-page></home-page>' }
const RegisterPage = { template: '<register></register>' }
const UserPage = { template: '<user-page></user-page>' }
const SellerPage = { template: '<seller-page></seller-page>' }
const AdminPage = { template: '<admin-page></admin-page>' }


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: HomePage},
		{ path: '/register', component: RegisterPage},
		{path: '/user',component:UserPage},
		{path: '/seller',component:SellerPage},
		{path: '/admin',component:AdminPage},
	  ]
});

Vue.use(VueGoogleMaps, {
	load: {
	  key: 'AIzaSyDs1wPwu4XZNfH5ttEj7md96W0nWJn0RGU',
	  v: '3.26',
	},
	// Demonstrating how we can customize the name of the components
	installComponents: false,
});

Vue.component('google-map', VueGoogleMaps.Map);
Vue.component('google-marker', VueGoogleMaps.Marker);

Vue.component('star-rating', VueStarRating.default);

var app = new Vue({
	router,
	el: '#webProjekat',
	components: {
		VueStarRating,
  },
});


