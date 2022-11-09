const { createApp } = Vue
createApp({
    data() {
        return {
            name: "",
            lastName: "",
            email: "",
            password:"",
            passwordConfirm:""
            }},
methods: {
    logIn(){
        console.log(this.email + " " + this.password)
        axios.post('/api/login?' + "email=" + this.email + "&password=" + this.password)
        .then(response =>
        window.location.href = "/web/accounts.html"
        ).catch(e => console.log (e.response.data))},

    logOut(){
        axios.post('/api/logout')
        .then(response =>
        window.location.href = "/public/index.html"
        )},

    register(){
            if (!(this.name=="" || this.lastName=="" || this.email=="" || this.password=="" || this.passwordConfirm=="")){
                if (this.password==this.passwordConfirm){
                axios.post('/api/clients?' + "name=" + this.name + "&lastName=" + this.lastName + "&email=" + this.email + "&password=" + this.password)
                        .then(response => this.logIn())
                        .catch(error => (console.log(error.response.data)))
                }else ( alert ("The passwords don't match"))


            }else( alert ("Error one of the boxes is empty") )

    }
    
    }

},
).mount('#app')
