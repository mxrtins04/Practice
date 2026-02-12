import React from 'react'
import styles from './bookList.module.css'
import mockBookList from './mockBookList'

const BookList = () => {
    return (
        	<div className={styles.wrapper}>
	    <header>
	    	<div className={styles['page-banner']}>
	    		<h1 className={styles.title}> Book Collections</h1>
          <p>Books</p>
          <form className={styles['search-books']}>
            <input type="text" placeholder="Search books..." />
          </form>
	    	</div>
	    </header>
	    <div className={styles['book-list']}>
	    	<h2 className={styles.title}>Books to Read</h2>
	    	<ul>
	    		{/* <li>
	    			<span className={styles.name}>Name of the Wind</span>
	    			<span className={styles.delete}>delete</span>
	    		</li>
	    		<li>
	    			<span className={styles.name}>The Wise Man's Fear</span>
	    			<span className={styles.delete}>delete</span>
	    		</li>
	    		<li>
	    			<span className={styles.name}>Kafka on the Shore</span>
	    			<span className={styles.delete}>delete</span>
	    		</li>
	    		<li>
	    			<span className={styles.name}>The Master and the Margarita</span>
	    			<span className={styles.delete}>delete</span>
	    		</li> */}
	    	</ul>
	    </div>
	    <form className={styles['add-book']}>
	    	<input type="text" placeholder="Add a book..." />
	    	<button>Add</button>
	    </form>
    </div>
    )
}

export default BookList