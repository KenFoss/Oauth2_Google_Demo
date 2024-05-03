import {useState, useEffect} from 'react';

const Dash = () => {

  let [idToken, setIdToken] = useState('');


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

    // let fetchIdToken = async () => {
    //   try {
    //     const response = await fetch('https://openidconnect.googleapis.com/v1/userinfo', {
    //       headers: {
    //         'Authorization': `${params['token_type']} ${params['access_token']}`
    //       }
    //     });

    //     const data = await response.json();
    //     setIdToken(data);
    //   } catch (error) {
    //     console.error('Error fetching ID token:', error);
    //   }
    // }

    let fetchData = async() => {
      let response = await fetch('http://localhost:8090/oauth2/google',{
          method: 'POST',
          headers :{
            'Authorization': `${params['id_token']}`,
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

  useEffect(() => {
    console.log(idToken);
  }, [idToken])


  return(
    <div>
      <h1>You are Logged In!</h1>
    </div>
  )
}

export default Dash;