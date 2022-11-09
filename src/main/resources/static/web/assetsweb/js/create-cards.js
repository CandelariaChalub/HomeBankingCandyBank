const { createApp } = Vue
createApp({
    data() {
        return {
            client: [],
            cards:[],
            email:"",
            type:"",
            color:""
            }},
    created() {
        this.loadData()
    },
methods: {
    loadData(){
        axios.get('/api/clients/current').then(response =>{
            this.client = response.data
            this.cards = this.client.cards
            this.email = this.client.email

        })
    },
    createCard(){
        axios.post('/api/clients/current/cards?' + "type=" + this.type + "&color=" + this.color)
        .then(response =>
            window.location.href = "/web/cards.html"
            ).catch(response => alert("you already have three cards of that type"))
    },
    logOut() {
        axios.post('/api/logout').then(response =>
            window.location.href = "/public/index.html"
        )
    },
    }
},
).mount('#app')