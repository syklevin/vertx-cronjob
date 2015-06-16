require 'vertx/util/utils.rb'
# Generated from com.glt.cronjob.JobRegistrationCenter
module Cronjob
  #  Created by levin on 5/22/2015.
  class JobRegistrationCenter
    # @private
    # @param j_del [::Cronjob::JobRegistrationCenter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Cronjob::JobRegistrationCenter] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [String] address 
    # @return [::Cronjob::JobRegistrationCenter]
    def self.create(vertx=nil,address=nil)
      if vertx.class.method_defined?(:j_del) && address.class == String && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::ComGltCronjob::JobRegistrationCenter.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::java.lang.String.java_class]).call(vertx.j_del,address),::Cronjob::JobRegistrationCenter)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,address)"
    end
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
    # @param [String] name 
    # @param [String] cron 
    # @param [Hash{String => Object}] data 
    # @yield 
    # @return [self]
    def schedule(name=nil,cron=nil,data=nil)
      if name.class == String && cron.class == String && data.class == Hash && block_given?
        @j_del.java_method(:schedule, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCoreJson::JsonObject.java_class,Java::IoVertxCore::Handler.java_class]).call(name,cron,::Vertx::Util::Utils.to_json_object(data),(Proc.new { |event| yield(event != nil ? JSON.parse(event.encode) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling schedule(name,cron,data)"
    end
    # @param [String] name 
    # @return [self]
    def unschedule(name=nil)
      if name.class == String && !block_given?
        @j_del.java_method(:unschedule, [Java::java.lang.String.java_class]).call(name)
        return self
      end
      raise ArgumentError, "Invalid arguments when calling unschedule(name)"
    end
  end
end
