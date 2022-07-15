# Values referencing

## Example for resolving a single reference:

    From (values.yaml):
      global:
        secretName: 12

      value: "{{ .Values.global.secretName }}"

    To:
      value: "12"

Call:

    value: {{ include (printf "phxFunctions.%s.resolve" (index .Values "phx-helm-functions" "version")) (dict "value" .Values.value "values" .Values) | trim }}

## Example for resolving a reference to a list

    From (values.yaml):
      global:
        list:
          - changeMe
          - asd

      value: "{{ .Values.global.list }}"

    To:
      value:
        - changeMe
        - asd

Call:

    example: {{ include (printf "phxFunctions.%s.resolve" (index .Values "phx-helm-functions" "version")) (dict "value" .Values.list "values" .Values) | trim | nindent 0 }}

## Example call for creating environment variables with reference resolution

    From (values.yaml):
      global:
        value1: 12
        value2: "google.com"

      env:
        a: false
        b: "{{ .Values.global.value1 }}"
        test3: "https://www.{{ .Values.global.value2 }}/search?q=helm"

    To:
       env:
         - name: a
           value: "false"
         - name: b
           value: "12"
         - name: c
           value: "https://www.google.com/search?q=helm"

Call:

    env:
    {{ include (printf "phxFunctions.%s.resolveEnvVars" (index .Values "phx-helm-functions" "version")) (dict "env" .Values.env "values" .Values) | trim }}


## Example call for replacing placeholder anywhere in a string

    From (values.yaml):
      some:
        path: "google.com"
        path2: "search?q=helm"

     value: "http://{{ .Values.some.path }}/{{ .Values.some.path2 }}"

    to:
     value: "https://www.google.com/search?q=helm"

Call:

    value: {{ include (printf "phxFunctions.%s.resolveString" (index .Values "phx-helm-functions" "version")) (dict "value" .Values.value "values" .Values) | trim | quote }}

## Example call for creating backend configuration with reference resolution

    From (values.yaml):
      global:
        clusterName: "dev"

      ingress:
        backends:
        - path: /api/
          service: "backend-service-name.{{ .Values.global.clusterName }}.lkw-walter.com"
          port: 8080

    To:
        value: 
        - path: /api/
          backend:
            serviceName: "backend-service-name.dev.lkw-walter.com"
            servicePort: 8080

Call:

    value: {{ include (printf "phxFunctions.%s.resolveBackends" (index .Values "phx-helm-functions" "version")) (dict "backends" .Values.ingress.backends "values" .Values)  }}
