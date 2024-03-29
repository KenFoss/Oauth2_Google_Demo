import {useState, useEffect} from 'react';

const Dash = () => {


  // When the user is redirected here... get the token 
  useEffect(() => {
    var fragmentString = window.location.href.split('#')[1];
    // console.log(window.location.href.split('#'))
    let paramsList = fragmentString.split('&')
    let params = {}
    paramsList.forEach( entry =>{
      // console.log(`entry is ${entry}`)
      let [key,value] = entry.split('=');
      params[key] = value
    })

    let fetchData = async() => {
      let response = await fetch('http://localhost:8090/login/oauth2/google',{
          method: 'POST',
          headers :{
            'Authorization': `${params['token_type']} ${params['access_token']}`,
            'Content-Type': 'application/json'
          }
        }
      )

      response = await response.json()

      return response
    }
  
    console.log(params)
    console.log(fetchData())
  }, [])


  return(
    <div>
      <h1>You are Logged In!</h1>
    </div>
  )
}

export default Dash;