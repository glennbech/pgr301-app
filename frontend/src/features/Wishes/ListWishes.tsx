import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';
import { Wish } from '../../app/models/wish';

function Wishes() {
	const [wishes, setWishes] = useState<Wish[]>([{id: 1, title: 'sample', description:'ayy', url: 'hey'}]);
	const { isAuthenticated, getAccessTokenSilently } = useAuth0();

	useEffect(() => {
		if (isAuthenticated) {
			getAccessTokenSilently().then((token) => {
				const options = {
					headers: {
						Authorization: `Bearer ${token}`,
					},
				};
				axios.get('/wishes', options).then((response) => {
					setWishes(response.data);
				});
			});
		}
	}, [isAuthenticated, getAccessTokenSilently]);
	return (
		<>
			<h2>Recent wishes.. </h2>
			{wishes.length < 1 && <p>Be the first one to post a wish!</p>}
			{wishes.map((wish) => (
				<div className="wish">
					<h3>{wish.title}</h3>
					<p>{wish.description}</p>
				</div>
			))}
		</>
	);
}

export default Wishes;
