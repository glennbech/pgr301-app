import { useAuth0 } from '@auth0/auth0-react';
import React, { useEffect } from 'react';
import Wishes from '../../features/Wishes/ListWishes';
import Header from '../../features/Header/Header';
import CreateWish from '../../features/Wishes/CreateWish';

function App() {
	return (
		<div className="App">
			<Header />
			<div className="content">
				<CreateWish />
				<Wishes />
			</div>
		</div>
	);
}

export default App;
