(ns notorious.views)

(defn home-page []
  [:div.sections
    [:section
      [:input.noto-input.section__title {:type "text", :placeholder "Title"}]
      [:div.noto-textarea.section__body {:contentEditable true} "hi"]]

    [:section
      [:input.noto-input.section__title {:type "text", :placeholder "Title"}]
      [:div.noto-textarea.section__body {:contentEditable true} "hi"]]

    [:section
      [:input.noto-input.section__title {:type "text", :placeholder "Title"}]
      [:div.noto-textarea.section__body {:contentEditable true} "hi"]]

    [:section
      [:input.noto-input.section__title {:type "text", :placeholder "Title"}]
      [:div.noto-textarea.section__body {:contentEditable true} "hi"]]])
