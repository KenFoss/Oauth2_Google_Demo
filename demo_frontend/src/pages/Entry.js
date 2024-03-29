import {useState, useEffect} from 'react';

const Entry = () => {

  useEffect(() => {

    const fetchResponse = async () => {
      let response = await fetch("http://localhost:8090/login/csrf", {
        method: 'GET',
        headers:{
          "Content-Type" : 'applicaiton/json'
        }
      })

      // Log the response cookies
      const cookies = response.headers.get('Set-Cookie');
      console.log("Response Cookies:", cookies);

      response = await response.json();

      return response;
    }

    let getCsrf = fetchResponse();

    console.log(getCsrf);    
  }, [])

  const handleClick = () => {
    const fetchResponse = async () => {
      let response = await fetch("http://localhost:8090/login/csrf", {
        method: 'GET',
        headers:{
          "Content-Type" : 'application/json'
        }
      })

      // Log the response cookies
      const cookies = response.headers.get('Set-Cookie');
      console.log("Response Cookies:", cookies);

      response = await response.json();

      return response;
    }

    let getCsrf = fetchResponse();

    console.log(getCsrf);    
  }


  return (
    <div>
      <h1> Welcome! </h1>
      <input type="button" value="Create Account" onClick={() => handleClick()}/>
      <input type="button" value="Login"/>
    </div>
  )

}

export default Entry;