(ns ^:no-doc logseq.api
  (:require [frontend.db :as db]
            [frontend.state :as state]
            [datascript.core :as d]
            [cljs.reader]
            [frontend.db.query-dsl :as query-dsl]))

(defn ^:export q
  [query-string]
  (when-let [repo (state/get-current-repo)]
    (when-let [conn (db/get-conn repo)]
      (when-let [result (query-dsl/query repo query-string)]
        (clj->js @result)))))

(defn ^:export datascript_query
  [query & inputs]
  (when-let [repo (state/get-current-repo)]
    (when-let [conn (db/get-conn repo)]
      (let [query (cljs.reader/read-string query)
            result (apply d/q query conn inputs)]
        (clj->js result)))))

(def ^:export custom_query db/custom-query)
